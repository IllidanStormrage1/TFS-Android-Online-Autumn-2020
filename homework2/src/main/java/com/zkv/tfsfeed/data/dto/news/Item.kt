package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.wall.History

@Keep
class Item(
    @SerializedName("attachments") val attachments: List<Attachment>?,
    @SerializedName("comments") val comments: Comments?,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: Likes?,
    @SerializedName("views") val views: Views?,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("reposts") val reposts: Reposts?,
    @SerializedName("source_id") val sourceId: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("text") val text: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("copy_history") val copyHistory: List<History>?,
)