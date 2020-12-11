package com.zkv.tfsfeed.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
class UserProfileEntity(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "lastSeenStatus") val lastSeenStatus: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String,
    @ColumnInfo(name = "about") val about: String,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "bdate") val bdate: String,
    @ColumnInfo(name = "homeTown") val homeTown: String,
    @ColumnInfo(name = "career") val career: String,
    @ColumnInfo(name = "education") val education: String,
    @ColumnInfo(name = "online") val online: Boolean,
    @ColumnInfo(name = "relation") val relation: Int,
    @ColumnInfo(name = "city") val city: String,
)
