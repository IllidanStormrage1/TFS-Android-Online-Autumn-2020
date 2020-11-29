package com.zkv.tfsfeed.data.converter

import com.zkv.tfsfeed.data.dto.news.Attachment
import com.zkv.tfsfeed.data.dto.news.Item
import com.zkv.tfsfeed.data.dto.news.NewsFeedResponse
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.currencyCountWithSuffix
import com.zkv.tfsfeed.domain.utils.dateStringFromTimeInMillis

object NewsResponseConverter {

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
            type = item.type ?: "",
            avatarUrl = avatarUrl,
            displayName = displayName ?: "",
            dateInMills = item.date * 1000L,
            date = dateStringFromTimeInMillis(item.date * 1000L),
            sourceId = item.sourceId,
            contentUrl = getAttachmentUrl(item.copyHistory != null, item),
            id = item.postId,
            text = if (item.text != null && item.text.isBlank()) item.copyHistory?.first()?.text
                ?: "" else item.text ?: "",
            commentsCount = item.comments?.count ?: 0,
            repostsCount = item.reposts?.count ?: 0,
            canLike = item.likes?.canLike ?: 1,
            likesCount = item.likes?.count ?: 0,
            viewsCount = item.views?.count?.currencyCountWithSuffix ?: "0",
            canPost = item.comments?.canPost == 1
        )
    }

    private fun getAttachmentUrl(isCopyHistory: Boolean, item: Item): String? =
        if (isCopyHistory)
            getContentUrl(item.copyHistory?.first()?.attachment?.first())
        else
            getContentUrl(item.attachments?.first())

    private fun getContentUrl(attachment: Attachment?): String? = when (attachment?.type) {
        "photo" -> attachment.photo?.sizes?.find { it.type == "r" }?.url
        "video" -> attachment.video?.images?.find { it.height == 450 }?.url
        else -> null
    }
}