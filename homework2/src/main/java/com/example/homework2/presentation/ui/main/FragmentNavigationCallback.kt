package com.example.homework2.presentation.ui.main

import com.example.homework2.domain.model.Post

interface FragmentNavigationCallback {
    fun navigateToDetail(item: Post)
    fun setVisibleFavoritesItem(isVisible: Boolean)
    fun showErrorDialog(throwable: Throwable)
}