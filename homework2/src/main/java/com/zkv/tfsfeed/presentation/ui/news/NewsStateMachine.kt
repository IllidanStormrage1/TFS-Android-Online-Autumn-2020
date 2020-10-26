package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.model.NewsItem

class NewsStateMachine {

    var state: NewsViewState = EmptyLoading()
        private set

    fun onLoading(): NewsViewState {
        state = when (state) {
            is EmptyLoading, is Loading -> state
            else -> {
                if (state.news.isNotEmpty())
                    Loading(state.news)
                else
                    EmptyLoading()
            }
        }
        return state
    }

    fun onLoaded(news: List<NewsItem>): NewsViewState {
        state = Loaded(news, news.isEmpty())
        return state
    }

    fun onError(throwable: Throwable): NewsViewState {
        state = if (state.news.isNotEmpty())
            Error(state.news, throwable.localizedMessage)
        else
            EmptyError
        return state
    }
}