package com.zkv.tfsfeed.data.converter

import com.zkv.tfsfeed.data.dto.comments.CommentsResponse
import com.zkv.tfsfeed.data.dto.news.Attachment
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.utils.dateStringFromTimeInMillis

object CommentsConverter {

    fun map(commentsResponse: CommentsResponse): List<Comment> =
        commentsResponse.items.map { item ->
            val displayName: String?
            val avatarUrl: String?
            if (item.fromId < 0) {
                val currentItemGroup = commentsResponse.groups.find { it.id == item.fromId * -1 }
                displayName = currentItemGroup?.name
                avatarUrl = currentItemGroup?.photo100
            } else {
                val currentItemProfile = commentsResponse.profiles.find { it.id == item.fromId }
                displayName = currentItemProfile?.run { "$firstName $lastName" }
                avatarUrl = currentItemProfile?.photo100
            }
            Comment(
                id = item.id,
                avatarUrl = avatarUrl ?: "",
                nickname = displayName ?: "",
                date = dateStringFromTimeInMillis(item.date * 1000L),
                text = item.text,
                likesCount = item.likes.count,
                contentUrl = getContentUrl(item.attachments?.first())
            )
        }

    private fun getContentUrl(attachment: Attachment?): String? = when (attachment?.type) {
        "photo" -> attachment.photo?.sizes?.find { it.type == "r" }?.url
        "video" -> attachment.video?.images?.find { it.height == 450 }?.url
        else -> null
    }
}