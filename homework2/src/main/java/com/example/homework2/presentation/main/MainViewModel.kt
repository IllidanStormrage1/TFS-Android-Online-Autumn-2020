package com.example.homework2.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework2.data.RepositoryImpl
import com.example.homework2.domain.MainRepository

class MainViewModel : ViewModel() {

    private val repositoryImpl: MainRepository = RepositoryImpl()

    private val _postsLiveData: MutableLiveData<UIState> = MutableLiveData()
    val postsLiveData: LiveData<UIState> get() = _postsLiveData

    init {
        onRefresh()
    }

    fun onRefresh() {
        _postsLiveData.value = UIState.Loading(true)
        val data = repositoryImpl.getAllPosts()
        _postsLiveData.value = UIState.Loading(false)
        _postsLiveData.value = UIState.Success(data)
    }

    fun like(id: Int) {
        repositoryImpl.likePost(id)
    }
}