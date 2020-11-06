package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.LocalRepositoryImpl
import com.zkv.tfsfeed.data.RemoteRepositoryImpl
import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.domain.MainInteractorImpl
import com.zkv.tfsfeed.domain.favorites.FavoritesInteractor
import com.zkv.tfsfeed.domain.favorites.FavoritesInteractorImpl
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindMainInteractor(interactorImpl: MainInteractorImpl): MainInteractor

    @Binds
    abstract fun bindFavoritesInteractor(interactorImpl: FavoritesInteractorImpl): FavoritesInteractor

    @Binds
    abstract fun bindLocalRepositoryImpl(localRepositoryImpl: LocalRepositoryImpl): LocaleRepository

    @Binds
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}