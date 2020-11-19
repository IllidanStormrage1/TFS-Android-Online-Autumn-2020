package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.model.NewsItem

sealed class NewsViewState(
    val news: MutableList<NewsItem> = mutableListOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
    val showEmptyLoaded: Boolean = false,
    var freshItemsAvailable: Boolean = false,
) {
    class Loading(news: List<NewsItem>) :
        NewsViewState(news = news.toMutableList(), showLoading = true, freshItemsAvailable = false)

    class EmptyLoading : NewsViewState(showEmptyLoading = true, freshItemsAvailable = false)

    class Loaded(news: List<NewsItem>, isEmpty: Boolean, freshItemsAvailable: Boolean) :
        NewsViewState(news = news.toMutableList(),
            showLoading = false,
            showEmptyLoading = false,
            showEmptyError = false,
            showEmptyLoaded = isEmpty,
            freshItemsAvailable = freshItemsAvailable)

    class Error(news: List<NewsItem>, message: String?) :
        NewsViewState(news = news.toMutableList(),
            showError = true,
            errorMessage = message,
            freshItemsAvailable = false)

    class EmptyError(message: String?) :
        NewsViewState(errorMessage = message, showEmptyError = true, freshItemsAvailable = false)
}