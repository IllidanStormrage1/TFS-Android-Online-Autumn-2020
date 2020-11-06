package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single

interface LocaleRepository {
    fun fetchSavedPosts(): Single<List<NewsItem>>
    fun rewriteNewsInDatabase(items: List<NewsItem>)
    fun deleteItemById(itemId: Int)
    fun putCurrentTimeInPrefs(time: Long)
    fun getRefreshTime(): Long
    fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int)
}