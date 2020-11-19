package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.repository.LocaleRepository
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchProfileInformation @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val remoteRepository: RemoteRepository,
) : (Boolean) -> Single<Profile> {

    override fun invoke(isRefresh: Boolean): Single<Profile> =
        if (isRefresh) syncProfileInformation() else loadSavedProfileInformation()

    private fun loadSavedProfileInformation(): Single<Profile> =
        localeRepository.fetchSavedProfile()
            .switchIfEmpty(syncProfileInformation())
            .subscribeOn(Schedulers.io())

    private fun syncProfileInformation(): Single<Profile> =
        remoteRepository.fetchProfileInformation()
            .doOnSuccess(localeRepository::rewriteProfileInformation)
            .subscribeOn(Schedulers.io())
}