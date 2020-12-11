package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single
import javax.inject.Inject

class FetchFavoritesPost @Inject constructor(private val fetchNewsFeed: FetchNewsFeed) :
    (Boolean) -> Single<List<NewsItem>> {

    override fun invoke(isRefresh: Boolean): Single<List<NewsItem>> =
        fetchNewsFeed(isRefresh)
            .map { items -> items.filter { it.canLike == 0 } }
}
