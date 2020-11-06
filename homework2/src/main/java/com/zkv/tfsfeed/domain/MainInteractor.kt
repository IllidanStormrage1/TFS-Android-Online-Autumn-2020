package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface MainInteractor {
    fun fetchAllPosts(forceRefresh: Boolean, time: Long?): Single<List<NewsItem>>
    fun likePost(
        itemId: Int,
        ownerId: Int,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable

    fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable
    fun isRelevanceNews(): Single<Boolean>
}