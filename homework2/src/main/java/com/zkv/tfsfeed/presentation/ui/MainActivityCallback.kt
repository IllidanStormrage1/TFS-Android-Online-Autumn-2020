package com.zkv.tfsfeed.presentation.ui

import com.zkv.tfsfeed.domain.model.NewsItem

interface MainActivityCallback {
    fun navigateToCreatorPost()
    fun navigateToDetail(item: NewsItem)
    fun shareNewsItem(item: NewsItem)
}
