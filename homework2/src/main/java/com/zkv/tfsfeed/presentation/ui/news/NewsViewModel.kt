package com.zkv.tfsfeed.presentation.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val stateMachine: NewsStateMachine,
) : BaseViewModel() {

    private val _newsViewStateLiveData: MutableLiveData<NewsViewState> = MutableLiveData()
    val newsViewStateLiveData: LiveData<NewsViewState> get() = _newsViewStateLiveData

    init {
        loadData(time = System.currentTimeMillis())
    }

    fun loadData(isRefresh: Boolean = false, time: Long? = null) {
        mainInteractor.fetchAllPosts(isRefresh, time)
            .flatMap { items -> mainInteractor.isRelevanceNews().map { items to it } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { items ->
                    updateState { stateMachine.onLoaded(items.first) }
                    updateState { stateMachine.updateFreshItemsAvailable(items.second) }
                },
                { throwable -> updateState { stateMachine.onError(throwable) } }
            )
            .also(compositeDisposable::add)
    }

    fun like(item: NewsItem) {
        mainInteractor.likePost(item.id, item.sourceId, item.type, item.canLike, item.likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { throwable -> updateState { stateMachine.onError(throwable) } })
            .also(compositeDisposable::add)
    }

    fun ignoreItem(item: NewsItem) {
        mainInteractor.ignoreItem(item.id, item.sourceId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ updateState { stateMachine.removeItem(item) } },
                { throwable -> updateState { stateMachine.onError(throwable) } })
            .also(compositeDisposable::add)
    }

    private fun updateState(stateAction: (NewsStateMachine) -> Unit) {
        stateAction.invoke(stateMachine)
        _newsViewStateLiveData.value = stateMachine.state
    }
}