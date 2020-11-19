package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.data.api.ErrorHandler
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile

class ProfileStateMachine(private val errorHandler: ErrorHandler) {

    var state: ProfileViewState = ProfileViewState.EmptyLoading()
        private set

    fun onLoading(): ProfileViewState {
        state = when (state) {
            is ProfileViewState.EmptyLoading, is ProfileViewState.Loading -> state
            else ->
                if (state.news.isNotEmpty() || state.profile != null)
                    ProfileViewState.Loading(state.news)
                else
                    ProfileViewState.EmptyLoading()
        }
        return state
    }

    fun onLoaded(profile: Profile, news: List<NewsItem>): ProfileViewState {
        state = ProfileViewState.Loaded(profile, news, false)
        return state
    }

    fun onError(throwable: Throwable): ProfileViewState {
        state = if (state.news.isNotEmpty())
            ProfileViewState.Error(state.news, errorHandler.getErrorMessage(throwable))
        else
            ProfileViewState.EmptyError(errorHandler.getErrorMessage(throwable))
        return state
    }

    fun removeItem(itemId: Int) {
        state.news.remove(state.news.find { it.id == itemId })
    }
}