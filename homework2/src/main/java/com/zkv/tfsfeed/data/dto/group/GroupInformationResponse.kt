package com.zkv.tfsfeed.data.dto.group

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class GroupInformationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
)
