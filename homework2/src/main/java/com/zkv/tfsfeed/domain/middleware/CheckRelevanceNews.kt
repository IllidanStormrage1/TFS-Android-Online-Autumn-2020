package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CheckRelevanceNews @Inject constructor(private val mediatorRepository: MediatorRepository) :
        () -> Single<Boolean> {

    override fun invoke(): Single<Boolean> =
        Single.fromCallable { System.currentTimeMillis() - 300000 > mediatorRepository.getLastRefreshTime() }
            .subscribeOn(Schedulers.io())
}