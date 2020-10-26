package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.model.NewsItem

sealed class NewsViewState(
    val news: List<NewsItem> = emptyList(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
    val showEmptyLoaded: Boolean = false,
)

class Loading(news: List<NewsItem>) : NewsViewState(news = news, showLoading = true)
class EmptyLoading : NewsViewState(showEmptyLoading = true)

class Loaded(news: List<NewsItem>, isEmpty: Boolean) : NewsViewState(news = news,
    showLoading = false,
    showEmptyLoading = false,
    showEmptyError = false,
    showEmptyLoaded = isEmpty)

class Error(news: List<NewsItem>, message: String?) :
    NewsViewState(news = news, showError = true, errorMessage = message)

object EmptyError : NewsViewState(showEmptyError = true)