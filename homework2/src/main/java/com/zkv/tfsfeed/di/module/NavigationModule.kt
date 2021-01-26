package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.presentation.navigation.NewsNavigator
import com.zkv.tfsfeed.presentation.navigation.ProfileNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NavigationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsNavigator() = NewsNavigator()

    @Provides
    @JvmStatic
    @Singleton
    fun provideProfileNavigator() = ProfileNavigator()
}
