package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CheckRelevanceNews @Inject constructor(private val mediatorRepository: MediatorRepository) :
        () -> Single<Boolean> {

    override fun invoke(): Single<Boolean> =
        Single.fromCallable { System.currentTimeMillis() - TIME_INTERVAL > mediatorRepository.getLastRefreshTime() }
            .subscribeOn(Schedulers.io())

    companion object {
        private val TIME_INTERVAL = TimeUnit.MINUTES.toMillis(5)
    }
}