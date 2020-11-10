package com.zkv.tfsfeed.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class NewsFeedResponse(
    @SerializedName("groups") val groups: List<Group>,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("profiles") val profiles: List<Profile>,
)