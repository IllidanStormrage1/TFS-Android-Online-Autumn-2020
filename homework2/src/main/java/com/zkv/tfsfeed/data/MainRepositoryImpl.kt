package com.zkv.tfsfeed.data

import com.zkv.tfsfeed.data.api.VkApi
import com.zkv.tfsfeed.data.converter.ResponseConverter
import com.zkv.tfsfeed.domain.MainRepository
import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val vkApi: VkApi) : MainRepository {

    override fun getAllPosts(): Single<List<NewsItem>> = vkApi.getNewsFeed()
        .map { ResponseConverter.map(it.newsFeedResponse) }

    override fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int) = if (canLike == 0)
        dislikePost(itemId, ownerId, type) else vkApi.addItemInLikes(itemId, ownerId, type)

    private fun dislikePost(itemId: Int, ownerId: Int, type: String) =
        vkApi.deleteItemFromLikes(itemId, ownerId, type)
}