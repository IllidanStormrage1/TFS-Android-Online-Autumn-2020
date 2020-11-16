package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.LocalRepositoryImpl
import com.zkv.tfsfeed.data.RemoteRepositoryImpl
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindLocalRepositoryImpl(localRepositoryImpl: LocalRepositoryImpl): LocaleRepository

    @Binds
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}