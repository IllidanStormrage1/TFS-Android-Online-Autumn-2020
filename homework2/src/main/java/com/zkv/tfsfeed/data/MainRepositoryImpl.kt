package com.zkv.tfsfeed.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.zkv.tfsfeed.data.api.VkApi
import com.zkv.tfsfeed.data.converter.ResponseConverter
import com.zkv.tfsfeed.data.database.dao.NewsFeedDAO
import com.zkv.tfsfeed.data.database.entity.EntityConverter
import com.zkv.tfsfeed.domain.MainRepository
import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val newsFeedDAO: NewsFeedDAO,
    @Named("default") private val preferences: SharedPreferences,
) : MainRepository {

    override fun fetchAllPosts(): Single<List<NewsItem>> = vkApi.getNewsFeed()
        .map { ResponseConverter.map(it.newsFeedResponse) }
        .doOnSuccess {
            newsFeedDAO.rewriteNews(it.map(EntityConverter::NewsFeedEntity))
        }

    override fun fetchSavedPosts(): Single<List<NewsItem>> = newsFeedDAO.getAllNewsFeed()
        .toObservable()
        .flatMapIterable { news -> news }
        .map(EntityConverter::NewsItem)
        .toList()

    override fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable =
        if (canLike == 0)
            vkApi.deleteItemFromLikes(itemId, ownerId, type) else vkApi.addItemInLikes(itemId,
            ownerId,
            type)

    override fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        vkApi.ignoreItem(itemId, ownerId, type).doOnComplete { newsFeedDAO.deleteByPostId(itemId) }

    override fun putCurrentTimeInPrefs(time: Long) {
        preferences.edit { putLong("time", time) }
    }

    override fun getRefreshTime(): Long = preferences.getLong("time", 0)

    override fun changeLikesInDatabase(id: Int, canLike: Int, likesCount: Int) =
        newsFeedDAO.updateLikesPost(id, canLike, likesCount)
}