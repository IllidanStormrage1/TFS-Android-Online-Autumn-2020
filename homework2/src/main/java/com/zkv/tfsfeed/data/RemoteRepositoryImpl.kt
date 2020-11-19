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
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val vkApi: VkApi) : RemoteRepository {

    override fun fetchAllPosts(): Single<List<NewsItem>> =
        vkApi.getNewsFeed()
            .map { NewsResponseConverter.map(it.newsFeedResponse) }

    override fun likePost(itemId: Int, ownerId: Int?, type: String, canLike: Int): Completable =
        if (canLike == 0)
            vkApi.deleteItemFromLikes(itemId, ownerId, type)
        else
            vkApi.addItemInLikes(itemId, ownerId, type)

    override fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        vkApi.ignoreItem(itemId, ownerId, type)

    override fun fetchProfileInformation(): Single<Profile> = vkApi.getProfileInfo()
        .map { it.profileFeedResponse.first() }
        .flatMap { response ->
            val groupId = response.career?.firstOrNull()?.groupId
            groupId?.let {
                getGroupInfo(it).map { groupResponse -> response to groupResponse }
            } ?: Single.just(response to null)
        }
        .map { ProfileResponseConverter.Profile(it.first, it.second) }

    override fun fetchUserWall(): Single<List<NewsItem>> = vkApi.getUserWall()
        .map { WallResponseConverter.NewsItem(it.newsWallResponse) }

    override fun removeUserPost(postId: Int): Completable = vkApi.deleteUserPost(postId = postId)

    override fun fetchComments(postId: Int, ownerId: Int): Single<List<Comment>> =
        vkApi.getCommentsForWall(postId = postId, ownerId = ownerId)
            .map { CommentsConverter.map(it.response) }

    override fun createPost(message: String): Completable = vkApi.createPost(message)

    private fun getGroupInfo(groupId: Int): Single<GroupInformationResponse> =
        vkApi.getGroupDescription(groupId)
            .map { it.response.first() }
}