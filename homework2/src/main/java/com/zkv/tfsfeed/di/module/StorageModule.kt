package com.zkv.tfsfeed.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.zkv.tfsfeed.BuildConfig.DATABASE_NAME
import com.zkv.tfsfeed.BuildConfig.ENC_PREF_FILE_NAME
import com.zkv.tfsfeed.BuildConfig.PREF_FILE_NAME
import com.zkv.tfsfeed.data.database.NewsFeedDatabase
import com.zkv.tfsfeed.data.database.dao.NewsFeedDao
import com.zkv.tfsfeed.data.database.dao.UserProfileDao
import com.zkv.tfsfeed.data.database.dao.UserWallDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object StorageModule {

    @Provides
    @JvmStatic
    @Singleton
    @Named("default")
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    @Provides
    @JvmStatic
    @Singleton
    fun provideMasterKey(context: Context) =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    @Provides
    @JvmStatic
    @Singleton
    @Named("encrypted")
    fun provideEncryptedSharedPreferences(
        context: Context,
        masterKey: MasterKey,
    ): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            ENC_PREF_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabase(context: Context): NewsFeedDatabase = Room
        .databaseBuilder(context, NewsFeedDatabase::class.java, DATABASE_NAME)
        .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsFeedDao(database: NewsFeedDatabase): NewsFeedDao = database.newsFeedDao

    @Provides
    @JvmStatic
    @Singleton
    fun provideUserProfileDao(database: NewsFeedDatabase): UserProfileDao = database.userProfileDao

    @Provides
    @JvmStatic
    @Singleton
    fun provideUserWallDao(database: NewsFeedDatabase): UserWallDao = database.userWallDao
}
