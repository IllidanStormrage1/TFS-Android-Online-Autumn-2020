package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.LocaleRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CheckRelevanceNews @Inject constructor(private val localeRepository: LocaleRepository) :
        () -> Single<Boolean> {

    override fun invoke(): Single<Boolean> =
        Single.fromCallable { System.currentTimeMillis() - 300000 > localeRepository.getRefreshTime() }
            .subscribeOn(Schedulers.io())
}