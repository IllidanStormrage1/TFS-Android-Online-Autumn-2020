package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(private val repository: MainRepository) :
    MainInteractor {

    override fun fetchFavoritesPosts(forceRefresh: Boolean): Single<List<NewsItem>> =
        fetchAllPosts(forceRefresh, null)
            .map { items -> items.filter { it.canLike == 0 } }
            .subscribeOn(Schedulers.io())

    override fun fetchAllPosts(forceRefresh: Boolean, time: Long?): Single<List<NewsItem>> =
        if (forceRefresh) syncNewsFeed(time) else loadSavedNewsFeed(time)

    override fun likePost(
        itemId: Int,
        ownerId: Int,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable =
        repository.likePost(itemId, ownerId, type, canLike)
            .doOnComplete { repository.changeLikesInDatabase(itemId, canLike, likesCount) }
            .subscribeOn(Schedulers.io())

    override fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        repository.ignoreItem(itemId, ownerId, type)
            .subscribeOn(Schedulers.io())

    private fun syncNewsFeed(time: Long? = null): Single<List<NewsItem>> =
        repository.fetchAllPosts()
            .map {
                time?.let(repository::putCurrentTimeInPrefs)
                it
            }
            .subscribeOn(Schedulers.io())

    private fun loadSavedNewsFeed(time: Long?): Single<List<NewsItem>> =
        repository.fetchSavedPosts()
            .flatMap { savedNews ->
                if (savedNews.isEmpty()) {
                    syncNewsFeed(time)
                } else {
                    Single.just(savedNews)
                }
            }
            .subscribeOn(Schedulers.io())

    override fun isRelevanceNews(): Single<Boolean> {
        return Single.just(System.currentTimeMillis() - 300000 > repository.getRefreshTime())
    }
}