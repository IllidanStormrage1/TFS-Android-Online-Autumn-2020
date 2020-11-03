package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface MainRepository {
    fun fetchAllPosts(): Single<List<NewsItem>>
    fun fetchSavedPosts(): Single<List<NewsItem>>
    fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable
    fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable
    fun putCurrentTimeInPrefs(time: Long)
    fun getRefreshTime(): Long
    fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int)
}