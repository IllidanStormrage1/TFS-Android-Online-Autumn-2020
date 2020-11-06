package com.zkv.tfsfeed.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.zkv.tfsfeed.data.database.dao.NewsFeedDAO
import com.zkv.tfsfeed.data.database.entity.EntityConverter
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class LocalRepositoryImpl @Inject constructor(
    private val dao: NewsFeedDAO,
    @Named("default") private val preferences: SharedPreferences,
) : LocaleRepository {

    override fun fetchSavedPosts(): Single<List<NewsItem>> = dao.getAllNewsFeed()
        .toObservable()
        .flatMapIterable { news -> news }
        .map(EntityConverter::NewsItem)
        .toList()

    override fun rewriteNewsInDatabase(items: List<NewsItem>) {
        dao.rewriteNews(items.map(EntityConverter::NewsFeedEntity))
    }

    override fun deleteItemById(itemId: Int) {
        dao.deleteByPostId(itemId)
    }

    override fun putCurrentTimeInPrefs(time: Long) {
        preferences.edit {
            putLong(PREF_KEY_TIME, time)
        }
    }

    override fun getRefreshTime(): Long =
        preferences.getLong(PREF_KEY_TIME, PREF_TIME_DEF_VALUE)

    override fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int) {
        dao.updateLikesPost(id, canLike, likesCount)
    }

    companion object {
        private const val PREF_KEY_TIME = "time"
        private const val PREF_TIME_DEF_VALUE = -1L
    }
}