package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(private val repository: MainRepository) :
    MainInteractor {

    override fun getFavoritesPosts(): Single<List<NewsItem>> =
        repository.getAllPosts()
            .subscribeOn(Schedulers.io())
            .map { items -> items.filter { it.canLike == 0 } }

    override fun getAllPosts(): Single<List<NewsItem>> =
        repository.getAllPosts()
            .subscribeOn(Schedulers.io())

    override fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable =
        repository.likePost(itemId, ownerId, type, canLike)
            .subscribeOn(Schedulers.io())
}