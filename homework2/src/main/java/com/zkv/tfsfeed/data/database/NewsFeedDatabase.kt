package com.zkv.tfsfeed.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zkv.tfsfeed.data.database.dao.NewsFeedDAO
import com.zkv.tfsfeed.data.database.entity.NewsFeedEntity

@Database(entities = [NewsFeedEntity::class], version = 1, exportSchema = false)
abstract class NewsFeedDatabase : RoomDatabase() {

    abstract val newsFeedDAO: NewsFeedDAO
}