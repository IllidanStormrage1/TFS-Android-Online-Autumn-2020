package com.zkv.tfsfeed.data.api

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Named

class AccessTokenHelper @Inject constructor(
    @Named("encrypted") private val sharedPreferences: SharedPreferences,
) {

    val accessToken: String by lazy { retrieveToken() }

    fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    private fun retrieveToken() =
        sharedPreferences.getString(TOKEN_KEY, null) ?: throw IllegalStateException()

    fun clearToken() {
        sharedPreferences.edit { remove(TOKEN_KEY) }
    }

    companion object {
        private const val TOKEN_KEY = "tokenKey"
    }
}