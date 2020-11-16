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
) : (Boolean, Long?) -> Single<List<NewsItem>> {

    override fun invoke(isRefresh: Boolean, time: Long?) =
        if (isRefresh) syncNewsFeed(time) else loadSavedNewsFeed(time)

    private fun syncNewsFeed(time: Long? = null): Single<List<NewsItem>> =
        remoteRepository.fetchAllPosts()
            .map {
                time?.let(localeRepository::putCurrentTimeInPrefs)
                it
            }
            .doAfterSuccess(localeRepository::rewriteNewsDatabase)
            .subscribeOn(Schedulers.io())

    private fun loadSavedNewsFeed(time: Long?): Single<List<NewsItem>> =
        localeRepository.fetchSavedPosts()
            .flatMap { savedNews ->
                if (savedNews.isEmpty())
                    syncNewsFeed(time)
                else
                    Single.just(savedNews)
            }
            .subscribeOn(Schedulers.io())
}