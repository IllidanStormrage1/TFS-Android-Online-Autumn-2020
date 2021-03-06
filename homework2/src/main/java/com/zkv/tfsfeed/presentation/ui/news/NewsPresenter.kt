package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.middleware.CheckRelevanceNews
import com.zkv.tfsfeed.domain.middleware.FetchNewsFeed
import com.zkv.tfsfeed.domain.middleware.IgnorePost
import com.zkv.tfsfeed.domain.middleware.LikePost
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.base.BasePresenter
import com.zkv.tfsfeed.presentation.navigation.NewsNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(
    private val likePost: LikePost,
    private val ignorePost: IgnorePost,
    private val fetchNewsFeed: FetchNewsFeed,
    private val checkRelevanceNews: CheckRelevanceNews,
    private val stateMachine: NewsStateMachine,
    private val newsNavigator: NewsNavigator
) : BasePresenter<NewsView>() {

    init {
        stateMachine.eventHandler = viewState::renderEvent
    }

    override fun onFirstViewAttach() {
        loadData(isRefresh = false)
    }

    fun navigateToDetail(item: NewsItem) {
        newsNavigator.navigateToDetail(item)
    }

    fun loadData(isRefresh: Boolean) {
        compositeDisposable += fetchNewsFeed(isRefresh)
            .flatMap { items -> checkRelevanceNews().map { items to it } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState(Action.Loading) }
            .subscribe(
                { pair -> updateState(Action.Loaded(pair.first, pair.second)) },
                { throwable -> updateState(Action.Error(throwable)) }
            )
    }

    fun onLike(itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) {
        compositeDisposable += likePost(itemId, sourceId, type, canLike, likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState(Action.Error(throwable))
            }
    }

    fun ignoreItem(itemId: Int, sourceId: Int) {
        compositeDisposable += ignorePost(itemId, sourceId, "wall")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState(Action.Remove(itemId)) },
                { throwable -> updateState(Action.Error(throwable)) }
            )
    }

    private fun updateState(stateAction: Action) {
        viewState.render(stateMachine.handleUpdate(stateAction))
    }
}
