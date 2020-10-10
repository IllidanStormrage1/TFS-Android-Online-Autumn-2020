package com.example.homework2.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework2.data.RepositoryImpl
import com.example.homework2.domain.FavoritesInteractor
import com.example.homework2.domain.FavoritesInteractorImpl
import com.example.homework2.presentation.news.UIState

class FavoritesViewModel : ViewModel() {

    private val interactor: FavoritesInteractor = FavoritesInteractorImpl(RepositoryImpl)

    private val _postsLiveData: MutableLiveData<UIState> = MutableLiveData()
    val postsLiveData: LiveData<UIState> get() = _postsLiveData

    fun onRefresh() {
        _postsLiveData.value = UIState.Loading(true)
        val data = interactor.getFavoritesPosts()
        _postsLiveData.value = UIState.Success(data)
        _postsLiveData.value = UIState.Loading(false)
    }
}