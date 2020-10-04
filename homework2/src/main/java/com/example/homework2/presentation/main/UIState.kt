package com.example.homework2.presentation.main

import com.example.homework2.domain.Post

sealed class UIState {
    class Success(val payload: List<Post>) : UIState()
    class Loading(val isLoad: Boolean) : UIState()
}