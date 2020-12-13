package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile

data class ProfileViewState(
    val profile: Profile? = null,
    val news: List<NewsItem> = listOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
)

sealed class Action {
    object Loading : Action()
    class Loaded(val profile: Profile, val payload: List<NewsItem>) : Action()
    class Error(val throwable: Throwable?) : Action()
    class Remove(val id: Int) : Action()
}

sealed class Event {
    class ShowErrorDialog(val errorMessage: String) : Event()
}
