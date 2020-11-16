package com.zkv.tfsfeed.domain.model

data class Comment(
    val avatarUrl: String,
    val nickname: String,
    val date: String,
    val text: String,
    val likesCount: Int,
)