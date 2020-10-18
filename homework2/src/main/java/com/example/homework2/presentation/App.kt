package com.example.homework2.presentation

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.example.homework2.R

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initEmojiCompat()
    }

    private fun initEmojiCompat() {
        val fontRequest = FontRequest(
            "com.example.fontprovider",
            "com.example",
            "emoji compat Font Query",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)
    }
}