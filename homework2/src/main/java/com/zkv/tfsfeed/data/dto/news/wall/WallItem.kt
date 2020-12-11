package com.zkv.tfsfeed.data.dto.news.wall

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.Attachment
import com.zkv.tfsfeed.data.dto.news.Comments
import com.zkv.tfsfeed.data.dto.news.Likes
import com.zkv.tfsfeed.data.dto.news.Reposts
import com.zkv.tfsfeed.data.dto.news.Views

@Keep
class WallItem(
    @SerializedName("id") val postId: Int,
    @SerializedName("from_id") val fromId: Int,
    @SerializedName("date") val dateInMills: Long,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val likes: Likes?,
    @SerializedName("views") val views: Views?,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("comments") val comments: Comments?,
    @SerializedName("reposts") val reposts: Reposts?,
    @SerializedName("copy_history") val copyHistory: List<History>?,
    @SerializedName("post_type") val type: String?,
    @SerializedName("attachments") val attachments: List<Attachment>?,
)
