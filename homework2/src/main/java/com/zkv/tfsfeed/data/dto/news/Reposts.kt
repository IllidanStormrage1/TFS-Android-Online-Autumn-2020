package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Reposts(
    @SerializedName("count") val count: Int?,
)
