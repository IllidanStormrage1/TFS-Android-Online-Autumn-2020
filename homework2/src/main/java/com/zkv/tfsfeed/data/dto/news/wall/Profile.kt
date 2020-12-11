package com.zkv.tfsfeed.data.dto.news.wall

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Profile(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_100") val photo100: String,
)
