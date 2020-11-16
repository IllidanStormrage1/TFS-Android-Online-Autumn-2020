package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.api.ErrorHandler
import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import com.zkv.tfsfeed.presentation.ui.profile.ProfileStateMachine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StateMachineModule {

    @Provides
    @JvmStatic
    fun provideNewsStateMachine(errorHandler: ErrorHandler): NewsStateMachine =
        NewsStateMachine(errorHandler)

    @Provides
    @JvmStatic
    @Singleton
    fun provideProfileStateMachine(errorHandler: ErrorHandler): ProfileStateMachine =
        ProfileStateMachine(errorHandler)
}