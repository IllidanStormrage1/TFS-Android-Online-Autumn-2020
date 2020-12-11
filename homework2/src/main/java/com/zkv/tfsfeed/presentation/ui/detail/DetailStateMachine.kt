package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.presentation.base.BaseViewStateMachine

class DetailStateMachine(private val simpleErrorHandler: SimpleErrorHandler) :
    BaseViewStateMachine<DetailViewState, Action>() {

    override var state: DetailViewState = DetailViewState()

    override fun handleUpdate(action: Action): DetailViewState {
        state = when (action) {
            is Action.Loading -> state.copy(showLoading = true)
            is Action.Loaded -> state.copy(comments = action.payload, showLoading = false)
            is Action.Error -> state.copy(
                errorMessage = simpleErrorHandler.getErrorMessage(action.throwable),
                showLoading = false,
                showError = true,
            )
        }
        return state
    }
}
