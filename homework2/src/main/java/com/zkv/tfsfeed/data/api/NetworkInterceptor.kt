package com.zkv.tfsfeed.data.api

import com.zkv.tfsfeed.BuildConfig.API_VERSION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(private val accessTokenHelper: AccessTokenHelper) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url.newBuilder()
            .addQueryParameter(ACCESS_TOKEN, accessTokenHelper.accessToken)
            .addQueryParameter(VERSION_CHAR, API_VERSION)
            .build()

        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val VERSION_CHAR = "v"
    }
}