package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchNewsFeed @Inject constructor(
    private val mediatorRepository: MediatorRepository,
) : (Boolean) -> Single<List<NewsItem>> {

    override fun invoke(isRefresh: Boolean): Single<List<NewsItem>> =
        mediatorRepository.fetchNewsFeed(isRefresh)
            .subscribeOn(Schedulers.io())
}