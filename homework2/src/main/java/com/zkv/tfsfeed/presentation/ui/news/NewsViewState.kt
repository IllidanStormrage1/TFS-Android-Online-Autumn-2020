package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.model.NewsItem

data class NewsViewState(
    val news: List<NewsItem> = listOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
    val showEmptyLoaded: Boolean = false,
    var freshItemsAvailable: Boolean = false,
)

sealed class Action(
    val payload: List<NewsItem> = emptyList(),
    val throwable: Throwable? = null,
    val id: Int? = null,
) {
    object Loading : Action()
    class Loaded(payload: List<NewsItem>) : Action(payload = payload)
    class Error(throwable: Throwable?) : Action(throwable = throwable)
    class Remove(id: Int) : Action(id = id)
}
