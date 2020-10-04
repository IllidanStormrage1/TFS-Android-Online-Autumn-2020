package com.example.homework2.domain

data class Post(
    val avatarUrl: String?,
    val displayName: String,
    val dateInMills: Long,
    val date: String,
    val sourceId: Int,
    val photoUrl: String?,
    val id: Int,
    val text: String,
    val viewCount: Int,
    val commentsCount: Int,
    val repostsCount: Int,
    var isFavorite: Boolean,
    var likesCount: Int,
)
