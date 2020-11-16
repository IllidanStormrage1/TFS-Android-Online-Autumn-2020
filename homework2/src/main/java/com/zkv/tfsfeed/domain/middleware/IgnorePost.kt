package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IgnorePost @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val remoteRepository: RemoteRepository,
) : (Int, Int, String) -> Completable {

    override fun invoke(itemId: Int, ownerId: Int, type: String): Completable =
        remoteRepository.ignoreItem(itemId, ownerId, type)
            .doOnComplete { localeRepository.removeItemById(itemId) }
            .subscribeOn(Schedulers.io())
}