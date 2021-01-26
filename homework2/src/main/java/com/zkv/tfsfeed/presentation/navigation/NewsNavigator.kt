package com.zkv.tfsfeed.presentation.navigation

import com.zkv.tfsfeed.domain.model.NewsItem

class NewsNavigator {

    private var newsRouter: NewsRouter? = null

    fun setNewsRouter(newsRouter: NewsRouter) {
        this.newsRouter = newsRouter
    }

    fun navigateToDetail(item: NewsItem) {
        newsRouter?.navigateToDetail(item)
    }

    fun removeRouter() {
        newsRouter = null
    }
}
