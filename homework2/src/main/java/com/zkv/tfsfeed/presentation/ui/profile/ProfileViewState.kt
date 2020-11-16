package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile

sealed class ProfileViewState(
    val profile: Profile? = null,
    val news: MutableList<NewsItem> = mutableListOf(),
    val showLoading: Boolean = false,
    val showEmptyLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmptyError: Boolean = false,
    val errorMessage: String? = null,
) {
    class Loading(news: List<NewsItem>) :
        ProfileViewState(news = news.toMutableList(), showLoading = true)

    class EmptyLoading : ProfileViewState(showEmptyLoading = true)

    class Loaded(profile: Profile, news: List<NewsItem>, isEmpty: Boolean) :
        ProfileViewState(profile = profile,
            news = news.toMutableList(),
            showLoading = false,
            showEmptyLoading = false,
            showEmptyError = false)

    class Error(news: List<NewsItem>, message: String?) :
        ProfileViewState(
            news = news.toMutableList(),
            showError = true,
            errorMessage = message,
        )

    class EmptyError(message: String?) : ProfileViewState(showEmptyError = true,
        errorMessage = message)
}