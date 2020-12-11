package com.zkv.tfsfeed.data.api

import android.content.SharedPreferences
import androidx.core.content.edit

class AccessTokenHelper(private val sharedPreferences: SharedPreferences) {

    val accessToken: String by lazy(LazyThreadSafetyMode.NONE) { retrieveToken() }

    fun isTokenExpired() = accessToken == TOKEN_HAS_EXPIRED

    fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    fun clearToken() {
        sharedPreferences.edit { remove(TOKEN_KEY) }
    }

    private fun retrieveToken() =
        sharedPreferences.getString(TOKEN_KEY, TOKEN_HAS_EXPIRED) ?: throw IllegalStateException()

    companion object {
        private const val TOKEN_KEY = "tokenKey"
        private const val TOKEN_HAS_EXPIRED = "tokenHasExpired"
    }
}
