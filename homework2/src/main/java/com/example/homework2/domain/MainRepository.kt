package com.example.homework2.domain

interface MainRepository {
    fun getAllPosts(): List<Post>
    fun likePost(id: Int)
}