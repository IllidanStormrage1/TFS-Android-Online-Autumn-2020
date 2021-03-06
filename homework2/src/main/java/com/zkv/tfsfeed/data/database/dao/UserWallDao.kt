package com.zkv.tfsfeed.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zkv.tfsfeed.data.database.entity.UserWallEntity
import io.reactivex.Single

@Dao
interface UserWallDao {

    @Query("SELECT * FROM user_wall ORDER BY date_in_mills DESC")
    fun getAllUserWallNews(): Single<List<UserWallEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<UserWallEntity>)

    @Query("UPDATE user_wall SET can_like = :canLike ,likes_count= :likesCount WHERE id = :id")
    fun updateLikesPost(id: Int, canLike: Int, likesCount: Int)

    @Query("DELETE FROM user_wall WHERE id = :id")
    fun deleteByPostId(id: Int)

    @Query("DELETE FROM user_wall")
    fun clearTable()

    @Transaction
    fun rewriteTable(items: List<UserWallEntity>) {
        clearTable()
        insertAll(items)
    }
}
