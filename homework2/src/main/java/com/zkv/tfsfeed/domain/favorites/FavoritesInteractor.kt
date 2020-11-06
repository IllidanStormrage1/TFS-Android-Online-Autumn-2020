package com.zkv.tfsfeed.domain.favorites

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single

interface FavoritesInteractor {
    fun fetchFavoritesPosts(forceRefresh: Boolean): Single<List<NewsItem>>
}