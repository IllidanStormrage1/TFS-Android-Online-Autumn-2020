package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchProfileInformation @Inject constructor(private val remoteRepository: RemoteRepository) :
        (Boolean) -> Single<Profile> {

    override fun invoke(isRefresh: Boolean): Single<Profile> =
        remoteRepository.fetchProfileInformation()
            .subscribeOn(Schedulers.io())
}