package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Attachment(
    @SerializedName("type") val type: String,
    @SerializedName("photo") val photo: Photo?,
    @SerializedName("video") val video: Video?,
)