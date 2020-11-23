package com.zkv.tfsfeed.domain.middleware

import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchComments @Inject constructor(private val mediatorRepository: MediatorRepository) :
        (Int, Int?) -> Single<List<Comment>> {

    override fun invoke(postId: Int, ownerId: Int?): Single<List<Comment>> =
        mediatorRepository.fetchComments(postId, ownerId)
            .subscribeOn(Schedulers.io())
}