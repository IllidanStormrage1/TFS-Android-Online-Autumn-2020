package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.domain.middleware.*
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val fetchProfileInformation: FetchProfileInformation,
    private val removeUserPost: RemoveUserPost,
    private val fetchUserNewsFeed: FetchUserNewsFeed,
    private val likePost: LikePost,
    private val stateMachine: ProfileStateMachine,
    private val createPost: CreatePost,
) : BasePresenter<ProfileView>() {

    override fun onFirstViewAttach() {
        getProfileInfo()
    }

    fun getProfileInfo(isRefresh: Boolean = false) {
        compositeDisposable += fetchProfileInformation(isRefresh)
            .zipWith(fetchUserNewsFeed(isRefresh)) { profile: Profile, list: List<NewsItem> ->
                profile to list
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .subscribe(
                { pair ->
                    updateState { stateMachine.onLoaded(pair.first, pair.second) }
                },
                { throwable -> Timber.e(throwable);updateState { stateMachine.onError(throwable) } })
    }

    fun onDeletePost(postId: Int, sourceId: Int) {
        compositeDisposable += removeUserPost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState { stateMachine.removeItem(postId) } },
                { throwable ->
                    updateState { stateMachine.onError(throwable) }
                })
    }

    fun onLike(itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) {
        compositeDisposable += likePost(itemId, null, type, canLike, likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState { stateMachine.onError(throwable) }
            }
    }

    fun createPostAndRefresh(message: String) {
        compositeDisposable += createPost(message)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState { stateMachine.onLoading() } }
            .doOnComplete { getProfileInfo(true) }
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState { stateMachine.onError(throwable) }
            }
    }

    private inline fun updateState(stateAction: (ProfileStateMachine) -> Unit) {
        stateAction(stateMachine)
        viewState.render(stateMachine.state)
    }
}