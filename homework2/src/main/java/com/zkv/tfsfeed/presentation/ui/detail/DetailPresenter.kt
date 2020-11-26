package com.zkv.tfsfeed.presentation.ui.detail

import com.zkv.tfsfeed.domain.middleware.CreateComment
import com.zkv.tfsfeed.domain.middleware.FetchComments
import com.zkv.tfsfeed.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val fetchComments: FetchComments,
    private val createComment: CreateComment,
    private val stateMachine: DetailStateMachine,
) : BasePresenter<DetailView>() {

    fun getComments(postId: Int, ownerId: Int?) {
        compositeDisposable += fetchComments(postId, ownerId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { items -> updateState { stateMachine.onLoaded(items) } },
                { throwable -> updateState { stateMachine.onError(throwable) } })
    }

    fun createCommentAndRefresh(postId: Int, ownerId: Int?, message: String) {
        compositeDisposable += createComment(ownerId, postId, message)
            .andThen { getComments(postId, ownerId) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState { stateMachine.onError(throwable) }
            }
    }

    private inline fun updateState(stateAction: (DetailStateMachine) -> Unit) {
        stateAction(stateMachine)
        viewState.render(stateMachine.state)
    }
}