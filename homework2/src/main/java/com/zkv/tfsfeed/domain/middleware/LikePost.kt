package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LikePost @Inject constructor(private val mediatorRepository: MediatorRepository) :
        (Int, Int?, String, Int, Int) -> Completable {

    override fun invoke(
        itemId: Int,
        sourceId: Int?,
        type: String,
        canLike: Int,
        likesCount: Int,
    ): Completable = mediatorRepository.likePost(itemId, sourceId, type, canLike, likesCount)
        .subscribeOn(Schedulers.io())
}