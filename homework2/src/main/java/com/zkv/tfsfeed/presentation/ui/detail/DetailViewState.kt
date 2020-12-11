package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.domain.model.Comment

sealed class DetailViewState(
    val comments: List<Comment> = emptyList(),
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String? = null,
) {
    class Loading(comments: List<Comment>) :
        DetailViewState(comments = comments, showLoading = true)

    class Loaded(comments: List<Comment>) :
        DetailViewState(comments = comments, showLoading = false)

    class Error(comments: List<Comment>, message: String?) : DetailViewState(
        comments = comments,
        showError = true,
        errorMessage = message,
    )

    object InitialState : DetailViewState()
}
