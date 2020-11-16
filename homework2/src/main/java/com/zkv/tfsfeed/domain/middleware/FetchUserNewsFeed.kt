package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchUserNewsFeed @Inject constructor(private val remoteRepository: RemoteRepository) :
        (Boolean) -> Single<List<NewsItem>> {

    override fun invoke(isRefresh: Boolean): Single<List<NewsItem>> =
        remoteRepository.fetchUserWall()
            .subscribeOn(Schedulers.io())
}