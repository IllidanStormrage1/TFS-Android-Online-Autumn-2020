package com.zkv.tfsfeed.data.database.dao

import androidx.room.*
import com.zkv.tfsfeed.data.database.entity.UserProfileEntity
import io.reactivex.Maybe

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getProfile(): Maybe<UserProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: UserProfileEntity)

    @Query("DELETE FROM news_feed")
    fun clearTable()

    @Transaction
    fun rewriteProfile(profile: UserProfileEntity) {
        clearTable()
        insertProfile(profile)
    }
}