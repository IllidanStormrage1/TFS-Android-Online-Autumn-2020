package com.zkv.tfsfeed.domain

import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface MainRepository {
    fun getAllPosts(): Single<List<NewsItem>>
    fun likePost(itemId: Int, ownerId: Int, type: String, canLike: Int): Completable
}