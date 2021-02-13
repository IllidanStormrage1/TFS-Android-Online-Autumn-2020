package com.zkv.tfsfeed.di.component

import android.content.Context
import com.zkv.tfsfeed.di.module.*
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.main.MainActivity
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import com.zkv.tfsfeed.presentation.ui.profile.ProfileFragment
import com.zkv.tfsfeed.presentation.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class, ApplicationModule::class, StateMachineModule::class, NavigationModule::class])
interface AppComponent {

    fun inject(application: App)
    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(newsFragment: NewsFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(detailFragment: DetailFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}
