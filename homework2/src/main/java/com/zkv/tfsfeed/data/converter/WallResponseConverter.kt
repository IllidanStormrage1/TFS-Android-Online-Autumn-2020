@file:Suppress("FunctionName")

package com.zkv.tfsfeed.data.converter

import com.zkv.tfsfeed.data.dto.news.wall.NewsWallResponse
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.currencyCountWithSuffix
import com.zkv.tfsfeed.domain.utils.dateStringFromTimeInMillis

object WallResponseConverter {

    fun NewsItem(newsWallResponse: NewsWallResponse): List<NewsItem> =
        newsWallResponse.items.map { item ->
            val displayName: String?
            val avatarUrl: String?
            val ownerId: Int =
                if (item.copyHistory != null) item.copyHistory.first().ownerId else item.fromId

            if (ownerId < 0) {
                val currentItemGroup =
                    newsWallResponse.groups.find { it.id == item.copyHistory?.first()?.ownerId!! * -1 }
                displayName = currentItemGroup?.name
                avatarUrl = currentItemGroup?.photo100
            } else {
                val currentItemProfile =
                    newsWallResponse.profiles.find { it.id == item.fromId }
                displayName = currentItemProfile?.run { "$firstName $lastName" }
                avatarUrl = currentItemProfile?.photo100
            }
            NewsItem(
                type = item.type ?: "",
                avatarUrl = avatarUrl,
                displayName = displayName ?: "",
                dateInMills = item.dateInMills * 1000L,
                date = dateStringFromTimeInMillis(item.dateInMills * 1000L),
                sourceId = item.ownerId,
                contentUrl = item.attachments?.first()?.photo?.sizes?.find { it.type == "x" }?.url
                    ?: item.copyHistory?.first()?.attachment?.first()?.photo?.sizes?.find { it.type == "x" }?.url,
                id = item.postId,
                text = if (item.text.isBlank()) item.copyHistory?.first()?.text
                    ?: "" else item.text,
                commentsCount = item.comments.count,
                repostsCount = item.reposts.count,
                canLike = item.likes.canLike,
                likesCount = item.likes.count,
                viewsCount = item.views?.count?.currencyCountWithSuffix ?: "0",
                canPost = item.comments.canPost == 1
            )
        }
}