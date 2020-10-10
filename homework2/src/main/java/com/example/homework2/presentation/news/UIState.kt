package com.example.homework2.presentation.news

import com.example.homework2.domain.model.Post

sealed class UIState {
    class Success(val payload: List<Post>) : UIState()
    class Loading(val isLoad: Boolean) : UIState()
    object Empty : UIState()
}