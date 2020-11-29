package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class VideoThumb(
    @SerializedName("url") val url: String,
    @SerializedName("height") val height: Int,
)