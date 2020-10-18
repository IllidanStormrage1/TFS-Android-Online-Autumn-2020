package com.example.homework2.domain

import com.example.homework2.domain.model.Post
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MainInteractorImpl(private val repository: MainRepository) : MainInteractor {

    override fun getFavoritesPosts(): Single<List<Post>> =
        repository.getAllPosts()
            .subscribeOn(Schedulers.io())
            .map { items -> items.filter { it.isFavorite } }

    override fun getAllPosts(): Single<List<Post>> =
        repository.getAllPosts()
            .subscribeOn(Schedulers.io())

    override fun likePost(item: Post): Single<Boolean> =
        repository.likePost(item)
            .subscribeOn(Schedulers.io())
}