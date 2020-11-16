package com.zkv.tfsfeed.data.dto.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class City(
    @SerializedName("title") val title: String?,
)