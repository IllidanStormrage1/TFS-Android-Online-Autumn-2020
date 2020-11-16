package com.zkv.tfsfeed.data.dto.news.wall

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.Attachment

@Keep
class History(
    @SerializedName("text") val text: String,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("attachments") val attachment: List<Attachment>,
)