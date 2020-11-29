package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Video(
    @SerializedName("image") val images: List<VideoThumb>,
)