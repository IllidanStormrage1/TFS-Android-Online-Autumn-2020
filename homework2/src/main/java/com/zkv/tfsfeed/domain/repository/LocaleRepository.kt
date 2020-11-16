package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single

interface LocaleRepository {
    fun fetchSavedPosts(): Single<List<NewsItem>>
    fun rewriteNewsDatabase(items: List<NewsItem>)
    fun removeItemById(itemId: Int)
    fun putCurrentTimeInPrefs(time: Long)
    fun getRefreshTime(): Long
    fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int)
}