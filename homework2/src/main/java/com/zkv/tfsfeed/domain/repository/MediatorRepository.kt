package com.zkv.tfsfeed.domain.repository

import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import io.reactivex.Completable
import io.reactivex.Single

interface MediatorRepository {
    fun fetchNewsFeed(isRefresh: Boolean): Single<List<NewsItem>>
    fun fetchUserWall(isRefresh: Boolean): Single<List<NewsItem>>
    fun fetchProfileInformation(isRefresh: Boolean): Single<Profile>
    fun fetchComments(postId: Int, ownerId: Int): Single<List<Comment>>

    fun removeUserWallPost(postId: Int): Completable
    fun removeNewsFeedPost(postId: Int, ownerId: Int, type: String): Completable
    fun likePost(
        postId: Int,
        ownerId: Int?,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable

    fun createPost(message: String): Completable

    fun getLastRefreshTime(): Long
}