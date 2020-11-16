package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.wall.Profile

@Keep
class NewsFeedResponse(
    @SerializedName("groups") val groups: List<Group>,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("profiles") val profiles: List<Profile>,
)