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
    val freshItemsAvailable: Boolean = false,
)

sealed class Action(
    val payload: List<NewsItem> = emptyList(),
    val throwable: Throwable? = null,
    val id: Int? = null,
    val freshItemsAvailable: Boolean = false
) {
    object Loading : Action()
    class Loaded(payload: List<NewsItem>, freshItemsAvailable: Boolean) : Action(payload = payload, freshItemsAvailable = freshItemsAvailable)
    class Error(throwable: Throwable?) : Action(throwable = throwable)
    class Remove(id: Int) : Action(id = id)
}
