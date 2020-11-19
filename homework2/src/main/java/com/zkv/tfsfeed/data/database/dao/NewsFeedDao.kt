package com.zkv.tfsfeed.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zkv.tfsfeed.data.database.entity.NewsFeedEntity
import io.reactivex.Single

@Dao
interface NewsFeedDao {

    @Query("SELECT * FROM news_feed ORDER BY date_in_mills DESC")
    fun getAllNewsFeed(): Single<List<NewsFeedEntity>>

    @Insert
    fun insertAll(news: List<NewsFeedEntity>)

    @Query("UPDATE news_feed SET can_like = :canLike ,likes_count= :likesCount WHERE id = :id")
    fun updateLikesPost(id: Int, canLike: Int, likesCount: Int)

    @Query("DELETE FROM news_feed WHERE id = :id")
    fun deleteByPostId(id: Int)

    @Query("DELETE FROM news_feed")
    fun clearTable()

    @Transaction
    fun rewriteNews(news: List<NewsFeedEntity>) {
        clearTable()
        insertAll(news)
    }
}