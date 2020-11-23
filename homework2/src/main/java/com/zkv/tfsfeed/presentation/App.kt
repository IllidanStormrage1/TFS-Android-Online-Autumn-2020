package com.zkv.tfsfeed.presentation

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.zkv.tfsfeed.BuildConfig
import com.zkv.tfsfeed.data.api.AccessTokenHelper
import com.zkv.tfsfeed.di.component.AppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var accessTokenHelper: AccessTokenHelper

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
        initExpiredTokenHandler()
        initLogger()
        initRxJavaPlugin()
    }

    private fun initRxJavaPlugin() {
        RxJavaPlugins.setErrorHandler { Timber.e(it) }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
    }

    private fun initDI() {
        appComponent = AppComponent.create(this)
        appComponent.inject(this)
    }

    private fun initExpiredTokenHandler() {
        VK.addTokenExpiredHandler(handler = object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                accessTokenHelper.clearToken()
            }
        })
    }
}