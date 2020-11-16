package com.zkv.tfsfeed.di.component

import android.content.Context
import com.zkv.tfsfeed.di.module.ApplicationModule
import com.zkv.tfsfeed.di.module.NetworkModule
import com.zkv.tfsfeed.di.module.StateMachineModule
import com.zkv.tfsfeed.di.module.StorageModule
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.ui.MainActivity
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import com.zkv.tfsfeed.presentation.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ApplicationModule::class, StateMachineModule::class])
interface AppComponent {

    fun inject(application: App)
    fun inject(mainActivity: MainActivity)
    fun inject(newsFragment: NewsFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(profileFragment: ProfileFragment)

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