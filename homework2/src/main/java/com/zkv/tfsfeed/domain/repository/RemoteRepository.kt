package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteRepository {
    fun fetchAllPosts(): Single<List<NewsItem>>
    fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable
    fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable
}