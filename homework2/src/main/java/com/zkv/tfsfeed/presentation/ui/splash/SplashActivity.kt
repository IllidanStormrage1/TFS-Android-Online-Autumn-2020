package com.zkv.tfsfeed.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.zkv.tfsfeed.data.api.AccessTokenHelper
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.ui.MainActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var accessTokenHelper: AccessTokenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(View(this))

        if (!VK.isLoggedIn() || accessTokenHelper.isTokenExpired())
            VK.login(this, listOf(VKScope.WALL, VKScope.FRIENDS, VKScope.OFFLINE))
        else
            navigateToMainActivity()
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                accessTokenHelper.saveToken(token.accessToken)
                navigateToMainActivity()
            }

            override fun onLoginFailed(errorCode: Int) {
                finish()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
            finish()
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
