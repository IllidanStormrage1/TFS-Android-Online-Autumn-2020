package com.zkv.tfsfeed.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zkv.tfsfeed.data.database.dao.NewsFeedDao
import com.zkv.tfsfeed.data.database.dao.UserProfileDao
import com.zkv.tfsfeed.data.database.dao.UserWallDao
import com.zkv.tfsfeed.data.database.entity.NewsFeedEntity
import com.zkv.tfsfeed.data.database.entity.UserProfileEntity
import com.zkv.tfsfeed.data.database.entity.UserWallEntity

@Database(entities = [NewsFeedEntity::class, UserProfileEntity::class, UserWallEntity::class],
    version = 1,
    exportSchema = false)
abstract class NewsFeedDatabase : RoomDatabase() {
    abstract val newsFeedDao: NewsFeedDao
    abstract val userProfileDao: UserProfileDao
    abstract val userWallDao: UserWallDao
}