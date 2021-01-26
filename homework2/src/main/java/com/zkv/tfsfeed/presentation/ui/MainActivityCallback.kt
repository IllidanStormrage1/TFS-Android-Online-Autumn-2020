package com.zkv.tfsfeed.presentation.ui

import com.zkv.tfsfeed.domain.model.NewsItem

interface MainActivityCallback {
    fun shareNewsItem(item: NewsItem)
}
