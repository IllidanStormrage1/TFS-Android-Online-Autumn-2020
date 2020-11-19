package com.zkv.tfsfeed.data

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
import javax.inject.Inject
import javax.inject.Named

class LocalRepositoryImpl @Inject constructor(
    private val newsFeedDao: NewsFeedDao,
    private val userProfileDao: UserProfileDao,
    private val wallDao: UserWallDao,
    @Named("default") private val preferences: SharedPreferences,
) {

    fun fetchSavedPosts(): Single<List<NewsItem>> = newsFeedDao.getAllNewsFeed()
        .toObservable()
        .flatMapIterable { news -> news }
        .map(EntityConverter::NewsItem)
        .toList()

    fun rewriteNewsFeedTable(items: List<NewsItem>) {
        newsFeedDao.rewriteNews(items.map(EntityConverter::NewsFeedEntity))
    }

    fun removeItemById(itemId: Int) {
        newsFeedDao.deleteByPostId(itemId)
    }

    fun putCurrentTimeInPrefs() {
        preferences.edit {
            putLong(PREF_KEY_TIME, System.currentTimeMillis())
        }
    }

    fun removeUserWallPost(postId: Int) {
        wallDao.deleteByPostId(postId)
    }

    fun removeNewsFeedPost(postId: Int) {
        newsFeedDao.deleteByPostId(postId)
    }

    fun getRefreshTime(): Long =
        preferences.getLong(PREF_KEY_TIME, PREF_TIME_DEF_VALUE)

    fun changeLikeNewsFeedPost(id: Int, canLike: Int, likesCount: Int) {
        val data = preparedLikeData(canLike, likesCount)
        newsFeedDao.updateLikesPost(id, data.first, data.second)
    }

    fun changeLikeUserWallPost(id: Int, canLike: Int, likesCount: Int) {
        val data = preparedLikeData(canLike, likesCount)
        wallDao.updateLikesPost(id, data.first, data.second)
    }

    fun fetchSavedProfile(): Maybe<Profile> = userProfileDao.getProfile()
        .map(EntityConverter::Profile)

    fun rewriteProfileTable(profile: Profile) {
        userProfileDao.rewriteProfile(EntityConverter.UserProfileEntity(profile))
    }

    fun rewriteUserWallTable(items: List<NewsItem>) {
        wallDao.rewriteNews(items.map(EntityConverter::UserWallEntity))
    }

    private fun preparedLikeData(canLike: Int, likesCount: Int): Pair<Int, Int> {
        val currentCanLike = if (canLike == 0) 1 else 0
        val currentLikesCount = if (currentCanLike == 1) likesCount - 1 else likesCount + 1
        return currentCanLike to currentLikesCount
    }

    fun fetchSavedUserWall(): Single<List<NewsItem>> = wallDao.getAllUserWallNews()
        .toObservable()
        .flatMapIterable { news -> news }
        .map(EntityConverter::NewsItem)
        .toList()

    companion object {
        private const val PREF_KEY_TIME = "time"
        private const val PREF_TIME_DEF_VALUE = -1L
    }
}