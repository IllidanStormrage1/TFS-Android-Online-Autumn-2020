package com.zkv.tfsfeed.presentation

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.zkv.tfsfeed.data.api.AccessTokenHelper
import com.zkv.tfsfeed.di.component.AppComponent
import com.zkv.tfsfeed.di.component.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
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
        RxJavaPlugins.setErrorHandler { Log.e(it.toString(), it.localizedMessage, it) }
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }

    private fun initExpiredTokenHandler() {
        VK.addTokenExpiredHandler(
            handler = object : VKTokenExpiredHandler {
                override fun onTokenExpired() {
                    accessTokenHelper.clearToken()
                }
            }
        )
    }
}
