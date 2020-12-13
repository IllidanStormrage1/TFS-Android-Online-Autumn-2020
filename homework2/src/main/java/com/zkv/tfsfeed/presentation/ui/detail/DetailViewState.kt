package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.domain.model.Comment

data class DetailViewState(
    val comments: List<Comment> = emptyList(),
    val showLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed class Action {
    object Loading : Action()
    class Loaded(val payload: List<Comment>) : Action()
    class Error(val throwable: Throwable?) : Action()
}

sealed class Event {
    class ShowErrorDialog(val errorMessage: String) : Event()
}
