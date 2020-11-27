package com.zkv.tfsfeed.data.database.entity

import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile

object EntityConverter {

    fun NewsItem(newsFeedEntity: NewsFeedEntity): NewsItem {
        with(newsFeedEntity) {
            return NewsItem(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                contentUrl = photoUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
                canPost = canPostComments
            )
        }
    }

    fun NewsFeedEntity(newsItem: NewsItem): NewsFeedEntity {
        with(newsItem) {
            return NewsFeedEntity(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                photoUrl = contentUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
                canPostComments = canPost,
            )
        }
    }

    fun UserProfileEntity(profile: Profile): UserProfileEntity {
        with(profile) {
            return UserProfileEntity(
                userId = userId,
                nickname = nickname,
                lastSeenStatus = lastSeenStatus,
                avatarUrl = avatarUrl,
                about = about,
                followers = followers,
                bdate = bdate,
                homeTown = homeTown,
                career = career,
                education = education,
                online = online,
                relation = relation,
                city = city,
            )
        }
    }

    fun Profile(userProfileEntity: UserProfileEntity): Profile {
        with(userProfileEntity) {
            return Profile(
                userId = userId,
                nickname = nickname,
                lastSeenStatus = lastSeenStatus,
                avatarUrl = avatarUrl,
                about = about,
                followers = followers,
                bdate = bdate,
                homeTown = homeTown,
                career = career,
                education = education,
                online = online,
                relation = relation,
                city = city,
            )
        }
    }

    fun NewsItem(userWallEntity: UserWallEntity): NewsItem {
        with(userWallEntity) {
            return NewsItem(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                contentUrl = photoUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
                canPost = canPostComments,
            )
        }
    }

    fun UserWallEntity(newsItem: NewsItem): UserWallEntity {
        with(newsItem) {
            return UserWallEntity(
                type = type,
                avatarUrl = avatarUrl,
                displayName = displayName,
                dateInMills = dateInMills,
                date = date,
                sourceId = sourceId,
                photoUrl = contentUrl,
                id = id,
                text = text,
                commentsCount = commentsCount,
                repostsCount = repostsCount,
                canLike = canLike,
                likesCount = likesCount,
                viewsCount = viewsCount,
                canPostComments = canPost,
            )
        }
    }
}