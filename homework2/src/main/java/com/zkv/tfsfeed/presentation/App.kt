package com.zkv.tfsfeed.presentation

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.zkv.tfsfeed.data.AccessTokenHelper
import com.zkv.tfsfeed.di.component.AppComponent
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
        appComponent = AppComponent.create(this)
        appComponent.inject(this)
        initExpiredTokenHandler()
    }

    private fun initExpiredTokenHandler() {
        VK.addTokenExpiredHandler(handler = object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                accessTokenHelper.clearToken()
            }
        })
    }
}