package com.zkv.tfsfeed.data.dto.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Career(
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("from") val from: Int?,
    @SerializedName("position") val position: String?,
)