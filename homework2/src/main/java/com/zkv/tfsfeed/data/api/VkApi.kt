package com.zkv.tfsfeed.data.api

import com.zkv.tfsfeed.data.dto.comments.BaseCommentsResponse
import com.zkv.tfsfeed.data.dto.group.BaseGroupInformationResponse
import com.zkv.tfsfeed.data.dto.news.BaseNewsFeedResponse
import com.zkv.tfsfeed.data.dto.news.wall.BaseNewsWallResponse
import com.zkv.tfsfeed.data.dto.profile.BaseProfileResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    //  ========== NewsFeed ==========  //
    @GET("newsfeed.get")
    fun getNewsFeed(
        @Query("filters") filter: String = "post,photo",
        @Query("count") count: Int? = 50,
        @Query("start_from") startStr: String? = null,
    ): Single<BaseNewsFeedResponse>

    @GET("newsfeed.ignoreItem")
    fun ignoreItemNewsFeed(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
        @Query("type") type: String,
    ): Completable

    //  ========== Likes ==========  //
    @GET("likes.add")
    fun addLikes(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int? = null,
        @Query("type") type: String,
    ): Completable

    @GET("likes.delete")
    fun deleteLikes(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int? = null,
        @Query("type") type: String,
    ): Completable

    //  ========== Users ==========  //
    @GET("users.get")
    fun getUser(
        @Query("user_ids") userId: Int? = null,
        @Query("fields") fields: String = USERS_GET_FIELDS,
    ): Single<BaseProfileResponse>

    //  ========== Wall ==========  //
    @GET("wall.get")
    fun getUserWall(
        @Query("owner_id") ownerId: Int? = null,
        @Query("extended") extended: Int = 1,
    ): Single<BaseNewsWallResponse>

    @GET("wall.delete")
    fun deleteUserWallPost(
        @Query("owner_id") ownerId: Int? = null,
        @Query("post_id") postId: Int,
    ): Completable

    @GET("wall.getComments")
    fun getComments(
        @Query("extended") extended: Int = 1,
        @Query("owner_id") ownerId: Int?,
        @Query("post_id") postId: Int,
        @Query("count") count: Int = 50,
        @Query("need_likes") needLikes: Int = 1,
    ): Single<BaseCommentsResponse>

    @GET("wall.post")
    fun createPost(
        @Query("message") message: String,
    ): Completable

    @GET("wall.createComment")
    fun createComment(
        @Query("owner_id") ownerId: Int? = null,
        @Query("post_id") postId: Int,
        @Query("message") message: String,
    ): Completable

    //  ========== Groups ==========  //
    @GET("groups.getById")
    fun getGroupDescription(
        @Query("group_id") groupId: Int,
    ): Single<BaseGroupInformationResponse>

    companion object {
        private const val USERS_GET_FIELDS =
            "domain, first_name, last_name, photo_100, about, bdate, city, country, career," +
                " education, followers_count, last_seen, online, home_town, relation"
    }
}
