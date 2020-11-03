package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface MainInteractor {
    fun fetchFavoritesPosts(forceRefresh: Boolean = false): Single<List<NewsItem>>
    fun fetchAllPosts(forceRefresh: Boolean = false, time: Long?): Single<List<NewsItem>>
    fun likePost(
        itemId: Int,
        ownerId: Int,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable

    fun ignoreItem(itemId: Int, ownerId: Int, type: String = "wall"): Completable
    fun isRelevanceNews(): Single<Boolean>
}