package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoveUserPost @Inject constructor(private val mediatorRepository: MediatorRepository) :
    (Int) -> Completable {

    override fun invoke(postId: Int): Completable = mediatorRepository.removeUserWallPost(postId)
        .subscribeOn(Schedulers.io())
}
