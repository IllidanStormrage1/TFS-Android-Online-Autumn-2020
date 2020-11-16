package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LikePost @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val remoteRepository: RemoteRepository,
) : (Int, Int?, String, Int, Int) -> Completable {

    override fun invoke(
        itemId: Int,
        sourceId: Int?,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable = remoteRepository.likePost(itemId, sourceId, type, canLike)
        .doOnComplete { changeLikeInDatabase(itemId, canLike, likesCount) }
        .subscribeOn(Schedulers.io())

    private fun changeLikeInDatabase(itemId: Int, canLike: Int, likesCount: Int) {
        val currentCanLike = if (canLike == 0) 1 else 0
        val currentLikesCount = if (currentCanLike == 1) likesCount - 1 else likesCount + 1
        localeRepository.changeLikesInDatabase(itemId, currentCanLike, currentLikesCount)
    }
}