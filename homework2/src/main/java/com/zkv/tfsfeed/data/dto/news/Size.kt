package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Size(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
)
