package com.example.homework2.presentation.ui.news

import com.example.homework2.domain.model.Post

sealed class UIState {
    class Loading(val isLoad: Boolean) : UIState()
    class Success(val payload: List<Post>) : UIState()
    class Failure(val throwable: Throwable) : UIState()
}
