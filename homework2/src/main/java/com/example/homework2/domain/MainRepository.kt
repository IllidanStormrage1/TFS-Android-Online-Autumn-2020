package com.example.homework2.domain

import com.example.homework2.domain.model.Post

interface MainRepository {
    fun getAllPosts(): List<Post>
    fun likePost(item: Post)
}