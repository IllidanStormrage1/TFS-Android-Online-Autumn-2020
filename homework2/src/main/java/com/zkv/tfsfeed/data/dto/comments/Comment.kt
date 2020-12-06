package com.zkv.tfsfeed.data.dto.comments

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.Attachment
import com.zkv.tfsfeed.data.dto.news.Likes

@Keep
class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("from_id") val fromId: Int,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("date") val date: Int,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val likes: Likes,
    @SerializedName("attachments") val attachments: List<Attachment>?,
    @SerializedName("deleted") val deleted: Boolean?,
)