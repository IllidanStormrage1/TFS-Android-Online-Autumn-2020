package com.zkv.tfsfeed.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.zkv.tfsfeed.data.database.dao.NewsFeedDao
import com.zkv.tfsfeed.data.database.dao.UserProfileDao
import com.zkv.tfsfeed.data.database.dao.UserWallDao
import com.zkv.tfsfeed.data.database.entity.EntityConverter
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import io.reactivex.Maybe
import io.reactivex.Single

class LocalRepositoryImpl(
    private val newsFeedDao: NewsFeedDao,
    private val userProfileDao: UserProfileDao,
    private val userWallDao: UserWallDao,
    private val preferences: SharedPreferences,
) {

    fun fetchNewsFeedPosts(): Single<List<NewsItem>> = newsFeedDao.getAllNewsFeed()
        .toObservable()
        .flatMapIterable { it }
        .map(EntityConverter::NewsItem)
        .toList()

    fun savedUserWallPosts(): Single<List<NewsItem>> = userWallDao.getAllUserWallNews()
        .toObservable()
        .flatMapIterable { it }
        .map(EntityConverter::NewsItem)
        .toList()

    fun fetchProfileInformation(): Maybe<Profile> = userProfileDao.getProfile()
        .map(EntityConverter::Profile)

    fun rewriteNewsFeedTable(items: List<NewsItem>) {
        newsFeedDao.rewriteTable(items.map(EntityConverter::NewsFeedEntity))
    }

    fun rewriteProfileTable(profile: Profile) {
        userProfileDao.rewriteTable(EntityConverter.UserProfileEntity(profile))
    }

    fun rewriteUserWallTable(items: List<NewsItem>) {
        userWallDao.rewriteTable(items.map(EntityConverter::UserWallEntity))
    }

    fun removeUserWallPost(postId: Int) {
        userWallDao.deleteByPostId(postId)
    }

    fun removeNewsFeedPost(postId: Int) {
        newsFeedDao.deleteByPostId(postId)
    }

    fun changeLikeNewsFeedPost(id: Int, canLike: Int, likesCount: Int) {
        val data = preparedLikeData(canLike, likesCount)
        newsFeedDao.updateLikesPost(id, data.first, data.second)
    }

    fun changeLikeUserWallPost(id: Int, canLike: Int, likesCount: Int) {
        val data = preparedLikeData(canLike, likesCount)
        userWallDao.updateLikesPost(id, data.first, data.second)
    }

    private fun preparedLikeData(canLike: Int, likesCount: Int): Pair<Int, Int> {
        val currentCanLike = if (canLike == 0) 1 else 0
        val currentLikesCount = if (currentCanLike == 1) likesCount - 1 else likesCount + 1
        return currentCanLike to currentLikesCount
    }

    fun putCurrentTimeInPrefs() {
        preferences.edit { putLong(PREF_KEY_TIME, System.currentTimeMillis()) }
    }

    fun getRefreshTime(): Long = preferences.getLong(PREF_KEY_TIME, PREF_TIME_DEF_VALUE)

    companion object {
        private const val PREF_KEY_TIME = "time"
        private const val PREF_TIME_DEF_VALUE = -1L
    }
}
