package com.example.homework2.presentation.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework2.data.RepositoryImpl
import com.example.homework2.domain.MainInteractor
import com.example.homework2.domain.MainInteractorImpl
import com.example.homework2.domain.model.Post
import com.example.homework2.presentation.base.BaseViewModel
import com.example.homework2.presentation.view.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class NewsViewModel : BaseViewModel() {

    private val interactor: MainInteractor = MainInteractorImpl(RepositoryImpl)

    private val _contentLiveData = MutableLiveData<UIState>()
    val contentLiveData: LiveData<UIState> get() = _contentLiveData

    private val _favoritesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val favoritesLiveData: LiveData<Boolean> get() = _favoritesLiveData

    init {
        loadData()
    }

    fun loadData() {
        interactor.getAllPosts()
            .delay(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _contentLiveData.value = UIState.Loading(true) }
            .subscribe(
                { list ->
                    _contentLiveData.value = UIState.Success(list)
                    _favoritesLiveData.value = list.any { it.isFavorite }
                },
                { throwable -> _contentLiveData.value = UIState.Failure(throwable) }
            )
            .addTo(compositeDisposable)
    }

    fun like(item: Post) {
        interactor.likePost(item)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(_favoritesLiveData::setValue)
            .addTo(compositeDisposable)
    }
}