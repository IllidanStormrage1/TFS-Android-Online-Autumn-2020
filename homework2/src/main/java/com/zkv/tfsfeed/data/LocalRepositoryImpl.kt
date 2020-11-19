package com.zkv.tfsfeed.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.zkv.tfsfeed.data.database.dao.NewsFeedDao
import com.zkv.tfsfeed.data.database.dao.UserProfileDao
import com.zkv.tfsfeed.data.database.entity.EntityConverter
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class LocalRepositoryImpl @Inject constructor(
    private val newsFeedDao: NewsFeedDao,
    private val userProfileDao: UserProfileDao,
    @Named("default") private val preferences: SharedPreferences,
) : LocaleRepository {

    override fun fetchSavedPosts(): Single<List<NewsItem>> = newsFeedDao.getAllNewsFeed()
        .toObservable()
        .flatMapIterable { news -> news }
        .map(EntityConverter::NewsItem)
        .toList()

    override fun rewriteNewsDatabase(items: List<NewsItem>) {
        newsFeedDao.rewriteNews(items.map(EntityConverter::NewsFeedEntity))
    }

    override fun removeItemById(itemId: Int) {
        newsFeedDao.deleteByPostId(itemId)
    }

    override fun putCurrentTimeInPrefs(time: Long) {
        preferences.edit {
            putLong(PREF_KEY_TIME, time)
        }
    }

    override fun getRefreshTime(): Long =
        preferences.getLong(PREF_KEY_TIME, PREF_TIME_DEF_VALUE)

    override fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int) {
        newsFeedDao.updateLikesPost(id, canLike, likesCount)
    }

    override fun fetchSavedProfile(): Maybe<Profile> = userProfileDao.getProfile()
        .map(EntityConverter::Profile)

    override fun rewriteProfileInformation(profile: Profile) {
        userProfileDao.rewriteProfile(EntityConverter.UserProfileEntity(profile))
    }

    companion object {
        private const val PREF_KEY_TIME = "time"
        private const val PREF_TIME_DEF_VALUE = -1L
    }
}