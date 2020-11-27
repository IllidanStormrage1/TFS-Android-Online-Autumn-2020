package com.zkv.tfsfeed.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_wall")
class UserWallEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "date_in_mills") val dateInMills: Long,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "source_id") val sourceId: Int,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "comments_count") val commentsCount: Int,
    @ColumnInfo(name = "reposts_count") val repostsCount: Int,
    @ColumnInfo(name = "can_like") val canLike: Int,
    @ColumnInfo(name = "likes_count") val likesCount: Int,
    @ColumnInfo(name = "views_count") val viewsCount: String,
    @ColumnInfo(name = "can_post_comment") val canPostComments: Boolean,
)