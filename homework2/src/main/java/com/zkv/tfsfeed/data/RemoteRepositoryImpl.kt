package com.zkv.tfsfeed.data

import com.zkv.tfsfeed.data.api.VkApi
import com.zkv.tfsfeed.data.converter.ResponseConverter
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val api: VkApi) : RemoteRepository {

    override fun fetchAllPosts(): Single<List<NewsItem>> =
        api.getNewsFeed()
            .map { ResponseConverter.map(it.newsFeedResponse) }

    override fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable =
        if (canLike == 0)
            api.deleteItemFromLikes(itemId, ownerId, type)
        else
            api.addItemInLikes(itemId, ownerId, type)

    override fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        api.ignoreItem(itemId, ownerId, type)
}