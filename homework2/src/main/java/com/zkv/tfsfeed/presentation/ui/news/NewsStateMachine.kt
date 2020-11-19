package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.data.api.ErrorHandler
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.ui.news.NewsViewState.*

class NewsStateMachine(private val errorHandler: ErrorHandler) {

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

    fun onLoaded(news: List<NewsItem>, fresh: Boolean): NewsViewState {
        state = Loaded(news.toList(), news.isEmpty(), fresh)
        return state
    }

    fun onError(throwable: Throwable): NewsViewState {
        state = if (state.news.isNotEmpty())
            Error(state.news, errorHandler.getErrorMessage(throwable))
        else
            EmptyError(errorHandler.getErrorMessage(throwable))
        return state
    }

    fun removeItem(itemId: Int) {
        state.news.remove(state.news.find { it.id == itemId })
    }
}