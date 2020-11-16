package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoveUserPost @Inject constructor(
    private val remoteRepository: RemoteRepository,
) : (Int) -> Completable {

    override fun invoke(postId: Int): Completable = remoteRepository.removeUserPost(postId)
        .subscribeOn(Schedulers.io())
}