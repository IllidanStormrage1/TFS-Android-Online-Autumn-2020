package com.zkv.tfsfeed.data.dto.group

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class BaseGroupInformationResponse(
    @SerializedName("response") val response: List<GroupInformationResponse>,
)