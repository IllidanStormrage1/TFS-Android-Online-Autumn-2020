package com.zkv.tfsfeed.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zkv.tfsfeed.data.database.entity.NewsFeedEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsFeedDAO {

    @Query("SELECT * FROM news_feed")
    fun getAllNewsFeed(): Single<List<NewsFeedEntity>>

    @Insert
    fun insertAll(news: List<NewsFeedEntity>)

    @Query("UPDATE news_feed SET can_like = :canLike ,likes_count= :likesCount WHERE id = :id")
    fun updateLikesPost(id: Int, canLike: Int, likesCount: Int): Completable

    @Query("DELETE FROM news_feed WHERE id = :id")
    fun deleteByPostId(id: Int): Completable

    @Query("DELETE FROM news_feed")
    fun clearTable()

    @Transaction
    fun rewriteNews(news: List<NewsFeedEntity>) {
        clearTable()
        insertAll(news)
    }
}