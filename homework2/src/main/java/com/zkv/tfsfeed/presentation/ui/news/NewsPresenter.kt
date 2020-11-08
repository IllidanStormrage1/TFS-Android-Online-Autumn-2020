package com.zkv.tfsfeed.presentation.ui.news

import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val stateMachine: NewsStateMachine,
) : BasePresenter<NewsView>() {


    override fun onFirstViewAttach() {
        loadData(isRefresh = false, time = System.currentTimeMillis())
    }

    fun loadData(isRefresh: Boolean, time: Long?) {
        compositeDisposable += mainInteractor.fetchAllPosts(isRefresh, time)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { items -> mainInteractor.isRelevanceNews().map { items to it } }
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { items ->
                    updateState { stateMachine.onLoaded(items.first) }
                    updateState { stateMachine.updateFreshItemsAvailable(items.second) }
                },
                { throwable -> updateState { stateMachine.onError(throwable) } })
    }

    fun like(itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) {
        compositeDisposable += mainInteractor.likePost(itemId, sourceId, type, canLike, likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},
                { throwable -> updateState { stateMachine.onError(throwable) } })
    }

    fun ignoreItem(itemId: Int, sourceId: Int) {
        compositeDisposable += mainInteractor.ignoreItem(itemId, sourceId, "wall")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ updateState { stateMachine.removeItem(itemId) } },
                { throwable -> updateState { stateMachine.onError(throwable) } })
    }

    private inline fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction.invoke(stateMachine)
        viewState.render(stateMachine.state)
    }
}