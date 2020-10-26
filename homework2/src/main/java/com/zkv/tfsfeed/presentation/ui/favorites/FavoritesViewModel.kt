package com.zkv.tfsfeed.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.presentation.addTo
import com.zkv.tfsfeed.presentation.base.BaseViewModel
import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import com.zkv.tfsfeed.presentation.ui.news.NewsViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val stateMachine: NewsStateMachine,
) :
    BaseViewModel() {

    private val _favoritesViewStateLiveData: MutableLiveData<NewsViewState> = MutableLiveData()
    val favoritesViewStateLiveData: LiveData<NewsViewState> get() = _favoritesViewStateLiveData

    init {
        onRefresh()
    }

    fun onRefresh() {
        mainInteractor.getFavoritesPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { list -> updateState { stateMachine.onLoaded(list) } },
                { throwable -> updateState { stateMachine.onError(throwable) } }
            )
            .addTo(compositeDisposable)
    }

    private fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction.invoke(stateMachine)
        _favoritesViewStateLiveData.value = stateMachine.state
    }
}