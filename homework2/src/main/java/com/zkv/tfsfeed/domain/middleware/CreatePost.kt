package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreatePost @Inject constructor(private val remoteRepository: RemoteRepository) :
        (String) -> Completable {

    override fun invoke(message: String): Completable = remoteRepository.createPost(message)
        .subscribeOn(Schedulers.io())
}