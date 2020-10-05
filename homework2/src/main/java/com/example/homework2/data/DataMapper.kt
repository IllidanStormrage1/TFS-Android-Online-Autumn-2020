package com.example.homework2.data

import com.example.homework2.data.dto.Response
import com.example.homework2.domain.Post
import com.example.homework2.domain.utils.dateStringFromTimeInMillis

fun Response.asDomainModel(): MutableList<Post> = items.map { item ->
    val displayName: String?
    val avatarUrl: String?
    if (item.sourceId < 0) {
        val currentItemGroup = groups.find { it.id == item.sourceId * -1 }
        displayName = currentItemGroup?.name
        avatarUrl = currentItemGroup?.photo100
    } else {
        val currentItemProfile = profiles.find { it.id == item.sourceId }
        displayName = currentItemProfile?.run { "$firstName $lastName" }
        avatarUrl = currentItemProfile?.photo100
    }
    Post(
        avatarUrl = avatarUrl,
        displayName = displayName ?: "",
        dateInMills = item.date * 1000L,
        date = dateStringFromTimeInMillis(item.date * 1000L),
        sourceId = item.sourceId,
        photoUrl = item.attachments?.first()?.photo?.sizes?.find { it.type == "x" }?.url,
        id = item.postId,
        text = item.text,
        viewCount = item.views.count,
        commentsCount = item.comments.count,
        repostsCount = item.reposts.count,
        isFavorite = item.isFavorite,
        likesCount = item.likes.count,
    )
}.toMutableList()
