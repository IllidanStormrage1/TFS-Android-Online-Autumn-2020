package com.zkv.tfsfeed.data.dto.comments

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.zkv.tfsfeed.data.dto.news.Group
import com.zkv.tfsfeed.data.dto.news.wall.Profile

@Keep
class CommentsResponse(
    @SerializedName("groups") val groups: List<Group>,
    @SerializedName("items") val items: List<Comment>,
    @SerializedName("profiles") val profiles: List<Profile>,
)
