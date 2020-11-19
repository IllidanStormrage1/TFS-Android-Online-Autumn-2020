package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteRepository {
    fun fetchProfileInformation(): Single<Profile>
    fun fetchAllPosts(): Single<List<NewsItem>>
    fun fetchUserWall(): Single<List<NewsItem>>
    fun fetchComments(postId: Int, ownerId: Int): Single<List<Comment>>
    fun likePost(itemId: Int, ownerId: Int?, type: String, canLike: Int): Completable
    fun createPost(message: String): Completable
    fun removeUserPost(postId: Int): Completable
    fun ignoreItem(itemId: Int, ownerId: Int, type: String): Completable
}