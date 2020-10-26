package com.zkv.tfsfeed.data.converter

import com.zkv.tfsfeed.data.dto.NewsFeedResponse
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.currencyCountWithSuffix
import com.zkv.tfsfeed.domain.utils.dateStringFromTimeInMillis

object ResponseConverter {

    fun map(newsFeedResponse: NewsFeedResponse) = newsFeedResponse.items.map { item ->
        val displayName: String?
        val avatarUrl: String?
        if (item.sourceId < 0) {
            val currentItemGroup = newsFeedResponse.groups.find { it.id == item.sourceId * -1 }
            displayName = currentItemGroup?.name
            avatarUrl = currentItemGroup?.photo100
        } else {
            val currentItemProfile = newsFeedResponse.profiles.find { it.id == item.sourceId }
            displayName = currentItemProfile?.run { "$firstName $lastName" }
            avatarUrl = currentItemProfile?.photo100
        }
        NewsItem(
            type = item.type,
            avatarUrl = avatarUrl,
            displayName = displayName ?: "",
            dateInMills = item.date * 1000L,
            date = dateStringFromTimeInMillis(item.date * 1000L),
            sourceId = item.sourceId,
            photoUrl = item.attachments?.first()?.photo?.sizes?.find { it.type == "x" }?.url,
            id = item.postId,
            text = item.text ?: "",
            commentsCount = item.comments.count,
            repostsCount = item.reposts.count,
            canLike = item.likes.canLike,
            likesCount = item.likes.count,
            viewsCount = item.views?.count?.currencyCountWithSuffix ?: "0"
        )
    }
}
