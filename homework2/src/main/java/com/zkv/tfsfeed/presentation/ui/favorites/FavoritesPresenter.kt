package com.zkv.tfsfeed.presentation.ui.favorites

import com.zkv.tfsfeed.domain.favorites.FavoritesInteractor
import com.zkv.tfsfeed.presentation.base.BasePresenter
import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(
    private val favoritesInteractor: FavoritesInteractor,
    private val stateMachine: NewsStateMachine,
) : BasePresenter<FavoritesView>() {

    override fun onFirstViewAttach() {
        onRefresh()
    }

    fun onRefresh(isRefresh: Boolean = false) {
        compositeDisposable += favoritesInteractor.fetchFavoritesPosts(isRefresh)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe({ list -> updateState { stateMachine.onLoaded(list) } },
                { throwable -> updateState { stateMachine.onError(throwable) } })
    }

    private inline fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction.invoke(stateMachine)
        viewState.render(stateMachine.state)
    }
}