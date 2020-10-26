package com.zkv.tfsfeed.presentation

import com.zkv.tfsfeed.domain.model.NewsItem

interface MainActivityCallback {
    fun navigateToDetail(item: NewsItem)
    fun showErrorDialog(throwable: Throwable)
}