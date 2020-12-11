package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreatePost @Inject constructor(private val mediatorRepository: MediatorRepository) :
    (String) -> Completable {

    override fun invoke(message: String): Completable = mediatorRepository.createPost(message)
        .subscribeOn(Schedulers.io())
}
