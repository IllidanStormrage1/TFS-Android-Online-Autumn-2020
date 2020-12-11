package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.presentation.base.BaseViewStateMachine

class NewsStateMachine(private val simpleErrorHandler: SimpleErrorHandler) :
    BaseViewStateMachine<NewsViewState, Action>() {

    override var state: NewsViewState = NewsViewState()

    override fun handleUpdate(action: Action): NewsViewState {
        state = when (action) {
            is Action.Loading -> state.copy(
                showError = false,
                showEmptyLoaded = false,
                showEmptyError = false,
                showLoading = state.news.isNotEmpty(),
                showEmptyLoading = state.news.isEmpty(),
            )
            is Action.Loaded -> state.copy(
                news = action.payload,
                showEmptyLoaded = action.payload.isEmpty(),
                showEmptyLoading = false,
                showLoading = false
            )
            is Action.Error -> state.copy(
                showEmptyLoading = false,
                showLoading = false,
                showEmptyLoaded = false,
                showEmptyError = state.news.isEmpty(),
                showError = state.news.isNotEmpty(),
                errorMessage = simpleErrorHandler.getErrorMessage(action.throwable),
            )
            is Action.Remove -> {
                val newList = state.news.toMutableList().also {
                    it.remove(state.news.find { item -> item.id == action.id })
                }
                state.copy(news = newList)
            }
        }
        return state
    }
}
