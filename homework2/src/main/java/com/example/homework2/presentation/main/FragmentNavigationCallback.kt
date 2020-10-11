package com.example.homework2.presentation.main

import com.example.homework2.domain.model.Post

interface FragmentNavigationCallback {
    fun navigateToDetail(item: Post)
    fun setVisibleFavoritesItem(isVisible: Boolean)
}