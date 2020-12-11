package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Photo(
    @SerializedName("sizes") val sizes: List<Size>,
)
