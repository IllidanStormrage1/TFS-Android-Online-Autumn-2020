package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchNewsFeed @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val remoteRepository: RemoteRepository,
) : (Boolean) -> Single<List<NewsItem>> {

    override fun invoke(isRefresh: Boolean): Single<List<NewsItem>> {
        val currentTime = System.currentTimeMillis()
        return if (isRefresh)
            syncNewsFeed(currentTime)
        else
            loadSavedNewsFeed(currentTime)
    }

    private fun syncNewsFeed(time: Long): Single<List<NewsItem>> =
        remoteRepository.fetchAllPosts()
            .map {
                localeRepository.putCurrentTimeInPrefs(time)
                it
            }
            .doOnSuccess(localeRepository::rewriteNewsDatabase)
            .subscribeOn(Schedulers.io())

    private fun loadSavedNewsFeed(time: Long): Single<List<NewsItem>> =
        localeRepository.fetchSavedPosts()
            .flatMap { savedNews ->
                if (savedNews.isEmpty())
                    syncNewsFeed(time)
                else
                    Single.just(savedNews)
            }
            .subscribeOn(Schedulers.io())
}