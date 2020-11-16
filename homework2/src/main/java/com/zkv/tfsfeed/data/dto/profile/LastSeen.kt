package com.zkv.tfsfeed.data.dto.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class LastSeen(
    @SerializedName("time") val time: Int,
)