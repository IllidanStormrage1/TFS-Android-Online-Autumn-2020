package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.presentation.base.BaseViewStateMachine

class ProfileStateMachine(private val simpleErrorHandler: SimpleErrorHandler) :
    BaseViewStateMachine<ProfileViewState, Action>() {

    override var state: ProfileViewState = ProfileViewState()

    override fun handleUpdate(action: Action): ProfileViewState {
        state = when (action) {
            is Action.Loading -> state.copy(
                showEmptyLoading = state.news.isEmpty() && state.profile == null,
                showLoading = state.news.isNotEmpty() || state.profile != null,
                showError = false,
                showEmptyError = false,
            )
            is Action.Loaded -> state.copy(
                news = action.payload,
                profile = action.profile,
                showLoading = false,
                showEmptyLoading = false,
            )
            is Action.Error -> state.copy(
                showEmptyError = state.news.isEmpty() && state.profile == null,
                showLoading = false,
                showEmptyLoading = false,
                showError = state.profile != null,
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
