package com.zkv.tfsfeed.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Views(
    @SerializedName("count") val count: Int,
)
