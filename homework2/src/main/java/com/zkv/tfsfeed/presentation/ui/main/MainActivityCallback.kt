package com.zkv.tfsfeed.presentation.ui.main

import com.zkv.tfsfeed.domain.model.NewsItem

interface MainActivityCallback {
    fun shareNewsItem(item: NewsItem)
}
