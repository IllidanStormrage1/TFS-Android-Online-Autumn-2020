package com.zkv.tfsfeed.domain.favorites

import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor(private val mainInteractor: MainInteractor) :
    FavoritesInteractor {

    override fun fetchFavoritesPosts(forceRefresh: Boolean): Single<List<NewsItem>> =
        mainInteractor.fetchAllPosts(forceRefresh, null)
            .map { items -> items.filter { it.canLike == 0 } }
            .subscribeOn(Schedulers.io())
}