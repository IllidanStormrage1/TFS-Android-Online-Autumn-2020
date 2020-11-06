package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val remoteRepository: RemoteRepository,
) : MainInteractor {

    override fun fetchAllPosts(forceRefresh: Boolean, time: Long?) =
        if (forceRefresh)
            syncNewsFeed(time)
        else
            loadSavedNewsFeed(time)

    override fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int, likesCount: Int) =
        remoteRepository.likePost(itemId, ownerId, type, canLike)
            .doOnComplete { changeLikeInDatabase(itemId, canLike, likesCount) }
            .subscribeOn(Schedulers.io())

    override fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable =
        remoteRepository.ignoreItem(itemId, ownerId, type)
            .doOnComplete { localeRepository.deleteItemById(itemId) }
            .subscribeOn(Schedulers.io())

    private fun changeLikeInDatabase(itemId: Int, canLike: Int, likesCount: Int) {
        val currentCanLike = if (canLike == 0) 1 else 0
        val currentLikesCount = if (currentCanLike == 1) {
            likesCount - 1
        } else {
            likesCount + 1
        }
        localeRepository.changeLikesInDatabase(itemId, currentCanLike, currentLikesCount)
    }

    private fun syncNewsFeed(time: Long? = null): Single<List<NewsItem>> =
        remoteRepository.fetchAllPosts()
            .map {
                time?.let(localeRepository::putCurrentTimeInPrefs)
                it
            }
            .doAfterSuccess(localeRepository::rewriteNewsInDatabase)
            .subscribeOn(Schedulers.io())

    private fun loadSavedNewsFeed(time: Long?): Single<List<NewsItem>> =
        localeRepository.fetchSavedPosts()
            .flatMap { savedNews ->
                if (savedNews.isEmpty()) {
                    syncNewsFeed(time)
                } else {
                    Single.just(savedNews)
                }
            }
            .subscribeOn(Schedulers.io())

    override fun isRelevanceNews(): Single<Boolean> {
        return Single.just(System.currentTimeMillis() - 300000 > localeRepository.getRefreshTime())
    }
}