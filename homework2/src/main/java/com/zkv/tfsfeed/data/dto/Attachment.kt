package com.zkv.tfsfeed.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Attachment(
    @SerializedName("photo") val photo: Photo?,
)