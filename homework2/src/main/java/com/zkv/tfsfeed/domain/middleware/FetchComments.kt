package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.repository.RemoteRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchComments @Inject constructor(private val remoteRepository: RemoteRepository) :
        (Int, Int) -> Single<List<Comment>> {

    override fun invoke(postId: Int, ownerId: Int): Single<List<Comment>> =
        remoteRepository.fetchComments(postId, ownerId)
            .subscribeOn(Schedulers.io())
}