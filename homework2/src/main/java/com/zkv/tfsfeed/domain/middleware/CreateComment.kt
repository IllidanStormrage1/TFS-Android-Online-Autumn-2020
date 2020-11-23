package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateComment @Inject constructor(
    private val mediatorRepository: MediatorRepository,
) : (Int?, Int, String) -> Completable {

    override fun invoke(ownerId: Int?, postId: Int, message: String): Completable =
        mediatorRepository.createComment(ownerId, postId, message)
            .subscribeOn(Schedulers.io())
}