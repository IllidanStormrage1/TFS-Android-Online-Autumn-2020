package com.example.homework2.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework2.data.RepositoryImpl
import com.example.homework2.domain.MainRepository
import com.example.homework2.domain.model.Post

class NewsViewModel : ViewModel() {
    private val repositoryImpl: MainRepository = RepositoryImpl

    private val _postsLiveData: MutableLiveData<UIState> = MutableLiveData()
    val postsLiveData: LiveData<UIState> get() = _postsLiveData

    private val _favoritesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val favoritesLiveData: LiveData<Boolean> get() = _favoritesLiveData

    init {
        onRefresh()
    }

    fun onRefresh() {
        _postsLiveData.value = UIState.Loading(true)
        val data = repositoryImpl.getAllPosts()
        _postsLiveData.value = UIState.Loading(false)
        _favoritesLiveData.value = data.any { it.isFavorite }
        _postsLiveData.value = UIState.Success(data)
    }

    fun like(item: Post) {
        _favoritesLiveData.value = repositoryImpl.likePost(item)
    }
}