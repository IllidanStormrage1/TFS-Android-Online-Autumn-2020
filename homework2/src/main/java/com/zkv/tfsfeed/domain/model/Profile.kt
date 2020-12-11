package com.zkv.tfsfeed.domain.model

data class Profile(
    val nickname: String,
    val lastSeenStatus: String,
    val userId: String,
    val avatarUrl: String,
    val about: String,
    val followers: Int,
    val bdate: String,
    val homeTown: String,
    val career: String,
    val education: String,
    val online: Boolean,
    val relation: Int,
    val city: String,
)
