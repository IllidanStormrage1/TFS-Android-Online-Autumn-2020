package com.example.homework2.domain

import com.example.homework2.domain.model.Post
import io.reactivex.Single

interface MainInteractor {
    fun getFavoritesPosts(): Single<List<Post>>
    fun getAllPosts(): Single<List<Post>>
    fun likePost(item: Post): Single<Boolean>
}