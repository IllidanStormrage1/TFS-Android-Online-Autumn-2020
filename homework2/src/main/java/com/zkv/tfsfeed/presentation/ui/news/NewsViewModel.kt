package com.zkv.tfsfeed.presentation.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.addTo
import com.zkv.tfsfeed.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val stateMachine: NewsStateMachine,
) :
    BaseViewModel() {

    private val _newsViewStateLiveData: MutableLiveData<NewsViewState> = MutableLiveData()
    val newsViewStateLiveData: LiveData<NewsViewState> get() = _newsViewStateLiveData

    init {
        loadData()
    }

    fun loadData() {
        mainInteractor.getAllPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { list -> updateState { stateMachine.onLoaded(list) } },
                { throwable -> updateState { stateMachine.onError(throwable) } }
            )
            .addTo(compositeDisposable)
    }

    fun like(item: NewsItem) {
        mainInteractor.likePost(item.id, item.sourceId, item.type, item.canLike)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { throwable -> updateState { stateMachine.onError(throwable) } })
            .addTo(compositeDisposable)
    }

    private fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction.invoke(stateMachine)
        _newsViewStateLiveData.value = stateMachine.state
    }
}