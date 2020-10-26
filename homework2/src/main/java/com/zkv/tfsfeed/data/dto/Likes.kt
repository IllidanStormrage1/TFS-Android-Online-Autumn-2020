package com.zkv.tfsfeed.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Likes(
    @SerializedName("count") val count: Int,
    @SerializedName("can_like") val canLike: Int,
)