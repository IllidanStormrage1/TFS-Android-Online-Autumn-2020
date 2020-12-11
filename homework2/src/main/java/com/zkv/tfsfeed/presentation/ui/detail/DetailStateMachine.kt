package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.domain.model.Comment

class DetailStateMachine(private val simpleErrorHandler: SimpleErrorHandler) {

    var state: DetailViewState = DetailViewState.InitialState
        private set

    fun onLoading(): DetailViewState {
        state = when (state) {
            is DetailViewState.Loading -> state
            else -> DetailViewState.Loading(state.comments)
        }
        return state
    }

    fun onLoaded(news: List<Comment>): DetailViewState {
        state = DetailViewState.Loaded(news)
        return state
    }

    fun onError(throwable: Throwable): DetailViewState {
        state = DetailViewState.Error(state.comments, simpleErrorHandler.getErrorMessage(throwable))
        return state
    }
}
