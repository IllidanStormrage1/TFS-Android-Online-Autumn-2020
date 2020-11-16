package com.zkv.tfsfeed.data.dto.news

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class BaseNewsFeedResponse(
    @SerializedName("response") val newsFeedResponse: NewsFeedResponse,
)

