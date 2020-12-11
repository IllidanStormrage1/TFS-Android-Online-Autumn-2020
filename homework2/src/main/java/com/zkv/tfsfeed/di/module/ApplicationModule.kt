package com.zkv.tfsfeed.di.module

import android.content.SharedPreferences
import com.zkv.tfsfeed.data.api.AccessTokenHelper
import com.zkv.tfsfeed.data.database.dao.NewsFeedDao
import com.zkv.tfsfeed.data.database.dao.UserProfileDao
import com.zkv.tfsfeed.data.database.dao.UserWallDao
import com.zkv.tfsfeed.data.repository.LocalRepositoryImpl
import com.zkv.tfsfeed.data.repository.MediatorRepositoryImpl
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
interface ApplicationModule {

    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideAccessTokenHelper(@Named("encrypted") sharedPreferences: SharedPreferences) =
            AccessTokenHelper(sharedPreferences)

        @Provides
        @JvmStatic
        @Singleton
        fun provideLocalRepository(
            newsFeedDao: NewsFeedDao,
            userProfileDao: UserProfileDao,
            userWallDao: UserWallDao,
            @Named("default") sharedPreferences: SharedPreferences
        ) = LocalRepositoryImpl(newsFeedDao, userProfileDao, userWallDao, sharedPreferences)
    }

    @Binds
    fun bindMediatorRepository(mediatorRepositoryImpl: MediatorRepositoryImpl): MediatorRepository
}
