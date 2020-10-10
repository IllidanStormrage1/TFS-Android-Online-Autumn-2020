package com.example.homework2.domain

import com.example.homework2.domain.model.Post

interface FavoritesInteractor {
    fun getFavoritesPosts(): List<Post>
}