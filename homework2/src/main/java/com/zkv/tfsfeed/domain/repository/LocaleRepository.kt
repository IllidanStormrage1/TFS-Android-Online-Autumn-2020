package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import io.reactivex.Maybe
import io.reactivex.Single

interface LocaleRepository {
    fun fetchSavedPosts(): Single<List<NewsItem>>
    fun rewriteNewsDatabase(items: List<NewsItem>)
    fun removeItemById(itemId: Int)
    fun putCurrentTimeInPrefs(time: Long)
    fun getRefreshTime(): Long
    fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int)
    fun fetchSavedProfile(): Maybe<Profile>
    fun rewriteProfileInformation(profile: Profile)
}