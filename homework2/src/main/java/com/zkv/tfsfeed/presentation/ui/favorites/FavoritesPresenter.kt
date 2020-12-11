package com.zkv.tfsfeed.presentation.ui.favorites

import com.zkv.tfsfeed.domain.middleware.FetchFavoritesPost
import com.zkv.tfsfeed.presentation.base.BasePresenter
import com.zkv.tfsfeed.presentation.ui.news.Action
import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(
    private val fetchFavoritesPost: FetchFavoritesPost,
    private val stateMachine: NewsStateMachine,
) : BasePresenter<FavoritesView>() {

    override fun onFirstViewAttach() {
        loadData(isRefresh = false)
    }

    fun loadData(isRefresh: Boolean) {
        compositeDisposable += fetchFavoritesPost(isRefresh)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState(Action.Loading) }
            .subscribe(
                { list -> updateState(Action.Loaded(list)) },
                { throwable -> updateState(Action.Error(throwable)) }
            )
    }

    private fun updateState(stateAction: Action) {
        viewState.render(stateMachine.handleUpdate(stateAction))
    }
}
