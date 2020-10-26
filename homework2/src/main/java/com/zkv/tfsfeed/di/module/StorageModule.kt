package com.zkv.tfsfeed.di.module

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.zkv.tfsfeed.BuildConfig.PREF_FILE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StorageModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideMasterKey(context: Context) =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideEncryptedSharedPreferences(context: Context, masterKey: MasterKey) =
        EncryptedSharedPreferences.create(
            context,
            PREF_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
}