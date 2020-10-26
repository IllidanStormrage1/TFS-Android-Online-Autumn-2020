package com.zkv.tfsfeed.data.api

import com.zkv.tfsfeed.data.dto.BaseNewsFeedResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.get")
    fun getNewsFeed(@Query("filters") filter: String = "post,photo"): Single<BaseNewsFeedResponse>

    @GET("likes.add")
    fun addItemInLikes(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
        @Query("type") type: String,
    ): Completable

    @GET("likes.delete")
    fun deleteItemFromLikes(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
        @Query("type") type: String,
    ): Completable
}