package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.repository.MediatorRepositoryImpl
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindMediatorRepository(mediatorRepositoryImpl: MediatorRepositoryImpl): MediatorRepository
}