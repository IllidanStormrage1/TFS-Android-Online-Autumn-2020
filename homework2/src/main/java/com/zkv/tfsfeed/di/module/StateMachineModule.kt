package com.zkv.tfsfeed.di.module

import com.zkv.tfsfeed.presentation.ui.news.NewsStateMachine
import dagger.Module
import dagger.Provides

@Module
object StateMachineModule {

    @Provides
    @JvmStatic
    fun provideNewsStateMachine(): NewsStateMachine = NewsStateMachine()
}