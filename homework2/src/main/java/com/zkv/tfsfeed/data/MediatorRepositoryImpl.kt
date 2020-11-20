package com.zkv.tfsfeed.data

import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MediatorRepositoryImpl @Inject constructor(
    private val localeRepository: LocalRepositoryImpl,
    private val remoteRepository: RemoteRepositoryImpl,
) : MediatorRepository {

    override fun fetchNewsFeed(isRefresh: Boolean): Single<List<NewsItem>> = if (isRefresh) {
        remoteRepository.fetchAllPosts()
            .doOnSuccess {
                localeRepository.rewriteNewsFeedTable(it)
                localeRepository.putCurrentTimeInPrefs()
            }
    } else {
        localeRepository.fetchNewsFeedPosts()
            .flatMap { if (it.isEmpty()) fetchNewsFeed(true) else Single.just(it) }
    }

    override fun fetchUserWall(isRefresh: Boolean): Single<List<NewsItem>> = if (isRefresh) {
        remoteRepository.fetchUserWall()
            .doOnSuccess(localeRepository::rewriteUserWallTable)
    } else {
        localeRepository.savedUserWallPosts()
            .flatMap { if (it.isEmpty()) fetchUserWall(true) else Single.just(it) }
    }

    override fun fetchProfileInformation(isRefresh: Boolean): Single<Profile> = if (isRefresh) {
        remoteRepository.fetchProfileInformation()
            .doOnSuccess(localeRepository::rewriteProfileTable)
    } else {
        localeRepository.fetchProfileInformation()
            .switchIfEmpty(fetchProfileInformation(true))
    }

    override fun fetchComments(postId: Int, ownerId: Int): Single<List<Comment>> =
        remoteRepository.fetchComments(postId, ownerId)

    override fun removeUserWallPost(postId: Int): Completable =
        remoteRepository.removeUserPost(postId)
            .doOnComplete { localeRepository.removeUserWallPost(postId) }

    override fun removeNewsFeedPost(postId: Int, ownerId: Int, type: String): Completable =
        remoteRepository.ignoreItem(postId, ownerId, type)
            .doOnComplete { localeRepository.removeNewsFeedPost(postId) }

    override fun likePost(
        postId: Int,
        ownerId: Int?,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable =
        remoteRepository.likePost(postId, ownerId, type, canLike)
            .doOnComplete {
                ownerId?.let {
                    localeRepository.changeLikeNewsFeedPost(postId, canLike, likesCount)
                } ?: localeRepository.changeLikeUserWallPost(postId, canLike, likesCount)
            }

    override fun createPost(message: String): Completable = remoteRepository.createPost(message)

    override fun getLastRefreshTime(): Long = localeRepository.getRefreshTime()
}