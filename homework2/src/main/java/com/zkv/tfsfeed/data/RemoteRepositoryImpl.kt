package com.zkv.tfsfeed.data

import com.zkv.tfsfeed.data.api.VkApi
import com.zkv.tfsfeed.data.converter.CommentsConverter
import com.zkv.tfsfeed.data.converter.NewsResponseConverter
import com.zkv.tfsfeed.data.converter.ProfileResponseConverter
import com.zkv.tfsfeed.data.converter.WallResponseConverter
import com.zkv.tfsfeed.data.dto.group.GroupInformationResponse
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val vkApi: VkApi) {

    fun fetchAllPosts(): Single<List<NewsItem>> =
        vkApi.getNewsFeed()
            .map { NewsResponseConverter.map(it.newsFeedResponse) }

    fun likePost(itemId: Int, ownerId: Int?, type: String, canLike: Int): Completable =
        if (canLike == 0)
            vkApi.deleteLikes(itemId, ownerId, type)
        else
            vkApi.addLikes(itemId, ownerId, type)

    fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        vkApi.ignoreItemNewsFeed(itemId, ownerId, type)

    fun fetchProfileInformation(): Single<Profile> = vkApi.getUser()
        .map { it.profileFeedResponse.first() }
        .flatMap { response ->
            val groupId = response.career?.firstOrNull()?.groupId
            groupId?.let {
                getGroupInfo(it).map { groupResponse -> response to groupResponse }
            } ?: Single.just(response to null)
        }
        .map { ProfileResponseConverter.Profile(it.first, it.second) }

    fun fetchUserWall(): Single<List<NewsItem>> = vkApi.getUserWall()
        .map { WallResponseConverter.NewsItem(it.newsWallResponse) }

    fun removeUserPost(postId: Int): Completable = vkApi.deleteUserWallPost(postId = postId)

    fun fetchComments(postId: Int, ownerId: Int): Single<List<Comment>> =
        vkApi.getComments(postId = postId, ownerId = ownerId)
            .map { CommentsConverter.map(it.response) }

    fun createPost(message: String): Completable = vkApi.createPost(message)

    private fun getGroupInfo(groupId: Int): Single<GroupInformationResponse> =
        vkApi.getGroupDescription(groupId)
            .map { it.response.first() }
}