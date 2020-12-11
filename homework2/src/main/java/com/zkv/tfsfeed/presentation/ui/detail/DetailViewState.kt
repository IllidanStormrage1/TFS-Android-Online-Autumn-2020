package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.domain.model.Comment

data class DetailViewState(
    val comments: List<Comment> = emptyList(),
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String? = null,
)

sealed class Action(
    val payload: List<Comment> = emptyList(),
    val throwable: Throwable? = null
) {
    object Loading : Action()
    class Loaded(payload: List<Comment>) : Action(payload = payload)
    class Error(throwable: Throwable?) : Action(throwable = throwable)
}
