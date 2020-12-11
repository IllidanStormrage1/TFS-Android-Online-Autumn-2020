package com.zkv.tfsfeed.data.dto.comments

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class BaseCommentsResponse(
    @SerializedName("response") val response: CommentsResponse,
)
