package com.zkv.tfsfeed.data.dto.news.wall

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class BaseNewsWallResponse(
    @SerializedName("response") val newsWallResponse: NewsWallResponse,
)
