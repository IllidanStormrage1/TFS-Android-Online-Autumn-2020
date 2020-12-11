package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.middleware.CheckRelevanceNews
import com.zkv.tfsfeed.domain.middleware.FetchNewsFeed
import com.zkv.tfsfeed.domain.middleware.IgnorePost
import com.zkv.tfsfeed.domain.middleware.LikePost
import com.zkv.tfsfeed.presentation.base.BasePresenter
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
) : BasePresenter<NewsView>() {

    override fun onFirstViewAttach() {
        loadData(isRefresh = false)
    }

    fun loadData(isRefresh: Boolean) {
        compositeDisposable += fetchNewsFeed(isRefresh)
            .flatMap { items -> checkRelevanceNews().map { items to it } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { pair -> updateState { stateMachine.onLoaded(pair.first, pair.second) } },
                { throwable -> updateState { stateMachine.onError(throwable) } }
            )
    }

    fun onLike(itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) {
        compositeDisposable += likePost(itemId, sourceId, type, canLike, likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState { stateMachine.onError(throwable) }
            }
    }

    fun ignoreItem(itemId: Int, sourceId: Int) {
        compositeDisposable += ignorePost(itemId, sourceId, "wall")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState { stateMachine.removeItem(itemId) } },
                { throwable -> updateState { stateMachine.onError(throwable) } }
            )
    }

    private inline fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction(stateMachine)
        viewState.render(stateMachine.state)
    }
}
