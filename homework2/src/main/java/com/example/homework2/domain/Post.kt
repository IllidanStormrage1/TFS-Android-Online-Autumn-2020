package com.example.homework2.domain

data class Post(
    val avatarUrl: String,
    val displayName: String,
    val date: Int,
    val sourceId: Int,
    val photosUrl: List<String>?,
    val id: Int,
    val text: String,
    val viewCount: Int,
    val commentsCount: Int,
    val repostsCount: Int,
    val isFavorite: Boolean,
    var likesCount: Int
)
