package com.zkv.tfsfeed.data.dto.news.wall

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.Group

@Keep
class NewsWallResponse(
    @SerializedName("groups") val groups: List<Group>,
    @SerializedName("items") val items: List<WallItem>,
    @SerializedName("profiles") val profiles: List<Profile>,
)