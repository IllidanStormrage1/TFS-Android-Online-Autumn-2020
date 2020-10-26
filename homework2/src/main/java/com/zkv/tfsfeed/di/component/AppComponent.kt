package com.zkv.tfsfeed.di.component

import android.content.Context
import com.zkv.tfsfeed.di.module.ApplicationModule
import com.zkv.tfsfeed.di.module.NetworkModule
import com.zkv.tfsfeed.di.module.StateMachineModule
import com.zkv.tfsfeed.di.module.StorageModule
import com.zkv.tfsfeed.di.module.viewmodel.ViewModelModule
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.MainActivity
import com.zkv.tfsfeed.presentation.base.BaseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ApplicationModule::class, ViewModelModule::class, StateMachineModule::class])
interface AppComponent {

    fun inject(application: App)
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setContext(context: Context): Builder

        fun build(): AppComponent
    }

    companion object {

        fun create(context: Context): AppComponent =
            with(DaggerAppComponent.builder()) {
                setContext(context)
                build()
            }
    }
}