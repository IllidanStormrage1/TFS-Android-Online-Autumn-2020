package com.zkv.tfsfeed.data.database.entity

import com.zkv.tfsfeed.domain.model.NewsItem

object EntityConverter {

    @Suppress("FunctionName")
    fun NewsItem(newsFeedEntity: NewsFeedEntity): NewsItem {
        with(newsFeedEntity) {
            return NewsItem(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                photoUrl = photoUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
            )
        }
    }

    @Suppress("FunctionName")
    fun NewsFeedEntity(newsItem: NewsItem): NewsFeedEntity {
        with(newsItem) {
            return NewsFeedEntity(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                photoUrl = photoUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
            )
        }
    }
}