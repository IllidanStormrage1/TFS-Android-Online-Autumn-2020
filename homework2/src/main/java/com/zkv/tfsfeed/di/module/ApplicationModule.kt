package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.MainRepositoryImpl
import com.zkv.tfsfeed.domain.MainInteractor
import com.zkv.tfsfeed.domain.MainInteractorImpl
import com.zkv.tfsfeed.domain.MainRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindMainInteractor(interactorImpl: MainInteractorImpl): MainInteractor

    @Binds
    abstract fun bindMainRepository(repositoryImpl: MainRepositoryImpl): MainRepository
}