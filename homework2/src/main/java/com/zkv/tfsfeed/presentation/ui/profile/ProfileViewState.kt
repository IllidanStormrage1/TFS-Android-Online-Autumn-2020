package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile

data class ProfileViewState(
    val profile: Profile? = null,
    val news: List<NewsItem> = listOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
)

sealed class Action(
    val payload: List<NewsItem> = emptyList(),
    val profile: Profile? = null,
    val throwable: Throwable? = null,
    val id: Int? = null,
) {
    object Loading : Action()
    class Loaded(profile: Profile, payload: List<NewsItem>) :
        Action(profile = profile, payload = payload)
    class Error(throwable: Throwable?) : Action(throwable = throwable)
    class Remove(id: Int) : Action(id = id)
}
