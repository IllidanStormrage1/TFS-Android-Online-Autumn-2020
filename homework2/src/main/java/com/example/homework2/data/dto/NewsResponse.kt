package com.example.homework2.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class NewsResponse(
    @SerializedName("response")
    val response: Response
)

@Keep
class Response(
    @SerializedName("groups")
    val groups: List<Group>,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("profiles")
    val profiles: List<Profile>
)

@Keep
class Group(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_100")
    val photo100: String,
)

@Keep
class Item(
    @SerializedName("attachments")
    val attachments: List<Attachment>?,
    @SerializedName("comments")
    val comments: Comments,
    @SerializedName("date")
    val date: Long,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("likes")
    val likes: Likes,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("reposts")
    val reposts: Reposts,
    @SerializedName("source_id")
    val sourceId: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("views")
    val views: Views
)

@Keep
class Attachment(
    @SerializedName("photo")
    val photo: Photo?
)

@Keep
class Photo(
    @SerializedName("sizes")
    val sizes: List<Size>
)

@Keep
class Size(
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
)

@Keep
class Comments(
    @SerializedName("count")
    val count: Int,
)

@Keep
class Likes(
    @SerializedName("count")
    val count: Int,
)

@Keep
class Reposts(
    @SerializedName("count")
    val count: Int,
)

@Keep
class Views(
    @SerializedName("count")
    val count: Int
)

@Keep
class Profile(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("photo_100")
    val photo100: String,
)
