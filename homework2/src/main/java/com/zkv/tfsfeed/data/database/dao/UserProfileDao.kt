package com.zkv.tfsfeed.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zkv.tfsfeed.data.database.entity.UserProfileEntity
import io.reactivex.Maybe

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getProfile(): Maybe<UserProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: UserProfileEntity)

    @Query("DELETE FROM user_profile")
    fun clearTable()

    @Transaction
    fun rewriteTable(profile: UserProfileEntity) {
        clearTable()
        insertProfile(profile)
    }
}
