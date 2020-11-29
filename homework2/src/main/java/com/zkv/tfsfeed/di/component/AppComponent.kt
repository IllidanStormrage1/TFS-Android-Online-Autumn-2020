package com.zkv.tfsfeed.di.component

import android.content.Context
import com.zkv.tfsfeed.di.module.ApplicationModule
import com.zkv.tfsfeed.di.module.NetworkModule
import com.zkv.tfsfeed.di.module.StateMachineModule
import com.zkv.tfsfeed.di.module.StorageModule
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import com.zkv.tfsfeed.presentation.ui.profile.ProfileFragment
import com.zkv.tfsfeed.presentation.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ApplicationModule::class, StateMachineModule::class])
interface AppComponent {

    fun inject(application: App)
    fun inject(splashActivity: SplashActivity)
    fun inject(newsFragment: NewsFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(detailFragment: DetailFragment)

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