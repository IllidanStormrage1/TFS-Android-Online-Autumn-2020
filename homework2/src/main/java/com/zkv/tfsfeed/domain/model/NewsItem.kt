package com.zkv.tfsfeed.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsItem(
    val type: String,
    val avatarUrl: String?,
    val displayName: String,
    val dateInMills: Long,
    val date: String,
    val sourceId: Int,
    val contentUrl: String?,
    val id: Int,
    val text: String,
    val commentsCount: Int,
    val repostsCount: Int,
    var canLike: Int,
    var likesCount: Int,
    val viewsCount: String,
    val canPost: Boolean,
) : Parcelable

fun NewsItem.like() {
    canLike = if (canLike == 0) {
        likesCount--
        1
    } else {
        likesCount++
        0
    }
}