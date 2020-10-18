package com.example.homework2.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework2.data.RepositoryImpl
import com.example.homework2.domain.MainInteractor
import com.example.homework2.domain.MainInteractorImpl
import com.example.homework2.presentation.base.BaseViewModel
import com.example.homework2.presentation.ui.news.UIState
import com.example.homework2.presentation.view.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class FavoritesViewModel : BaseViewModel() {

    private val interactor: MainInteractor = MainInteractorImpl(RepositoryImpl)

    private val _postsLiveData: MutableLiveData<UIState> = MutableLiveData()
    val postsLiveData: LiveData<UIState> get() = _postsLiveData

    init {
        onRefresh()
    }

    fun onRefresh() {
        interactor.getFavoritesPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _postsLiveData.value = UIState.Loading(true) }
            .subscribe(
                { list -> _postsLiveData.value = UIState.Success(list) },
                { throwable -> _postsLiveData.value = UIState.Failure(throwable) }
            )
            .addTo(compositeDisposable)
    }
}