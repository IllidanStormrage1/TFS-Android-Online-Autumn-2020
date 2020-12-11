package com.zkv.tfsfeed.di.module

import android.content.Context
import com.zkv.tfsfeed.BuildConfig
import com.zkv.tfsfeed.data.api.NetworkInterceptor
import com.zkv.tfsfeed.data.api.SimpleErrorHandler
import com.zkv.tfsfeed.data.api.VkApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideErrorHandler(context: Context): SimpleErrorHandler = SimpleErrorHandler(context)

    @Provides
    @JvmStatic
    @Singleton
    fun provideHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .addInterceptor(networkInterceptor)
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideVkApi(retrofit: Retrofit): VkApi = retrofit.create(VkApi::class.java)
}
