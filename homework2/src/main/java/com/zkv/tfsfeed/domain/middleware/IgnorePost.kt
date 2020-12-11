package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IgnorePost @Inject constructor(private val mediatorRepository: MediatorRepository) :
    (Int, Int, String) -> Completable {

    override fun invoke(itemId: Int, ownerId: Int, type: String): Completable =
        mediatorRepository.removeNewsFeedPost(itemId, ownerId, type)
            .subscribeOn(Schedulers.io())
}
