package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.model.NewsItem

data class NewsViewState(
    val news: List<NewsItem> = listOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
    val showEmptyLoaded: Boolean = false,
    val freshItemsAvailable: Boolean = false,
)

sealed class Action {
    object Loading : Action()
    class Loaded(val payload: List<NewsItem>, val freshItemsAvailable: Boolean) : Action()
    class Error(val throwable: Throwable?) : Action()
    class Remove(val id: Int) : Action()
}

sealed class Event {
    class ShowErrorDialog(val errorMessage: String) : Event()
}
