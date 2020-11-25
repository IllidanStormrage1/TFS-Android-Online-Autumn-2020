package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.presentation.ui.detail.DetailStateMachine
import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import com.zkv.tfsfeed.presentation.ui.profile.ProfileStateMachine
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StateMachineModule {

    @Provides
    @JvmStatic
    fun provideNewsStateMachine(simpleErrorHandler: SimpleErrorHandler): NewsStateMachine =
        NewsStateMachine(simpleErrorHandler)

    @Provides
    @JvmStatic
    @Singleton
    fun provideProfileStateMachine(simpleErrorHandler: SimpleErrorHandler): ProfileStateMachine =
        ProfileStateMachine(simpleErrorHandler)

    @Provides
    @JvmStatic
    fun provideDetailStateMachine(simpleErrorHandler: SimpleErrorHandler): DetailStateMachine =
        DetailStateMachine(simpleErrorHandler)
}