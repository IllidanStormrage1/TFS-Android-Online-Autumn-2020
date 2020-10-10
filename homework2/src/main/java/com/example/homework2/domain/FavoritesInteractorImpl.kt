package com.example.homework2.domain

class FavoritesInteractorImpl(private val repository: MainRepository) : FavoritesInteractor {

    override fun getFavoritesPosts() = repository.getAllPosts().filter { it.isFavorite }
}