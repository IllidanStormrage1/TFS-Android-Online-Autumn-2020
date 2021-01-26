package com.zkv.tfsfeed.presentation.ui.profile

import com.zkv.tfsfeed.data.api.NetworkHelper
import com.zkv.tfsfeed.domain.middleware.*
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.presentation.base.BasePresenter
import com.zkv.tfsfeed.presentation.navigation.NewsNavigator
import com.zkv.tfsfeed.presentation.navigation.ProfileNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val fetchProfileInformation: FetchProfileInformation,
    private val removeUserPost: RemoveUserPost,
    private val fetchUserNewsFeed: FetchUserNewsFeed,
    private val likePost: LikePost,
    private val stateMachine: ProfileStateMachine,
    private val createPost: CreatePost,
    private val networkHelper: NetworkHelper,
    private val newsNavigator: NewsNavigator,
    private val profileNavigator: ProfileNavigator
) : BasePresenter<ProfileView>() {

    init {
        stateMachine.eventHandler = viewState::renderEvent
    }

    override fun onFirstViewAttach() {
        getProfileInfo(isRefresh = networkHelper.isConnected)
    }

    fun navigateToDetail(item: NewsItem) {
        newsNavigator.navigateToDetail(item)
    }

    fun navigateToCreatorPost() {
        profileNavigator.navigateToCreatorPost()
    }

    fun getProfileInfo(isRefresh: Boolean) {
        compositeDisposable += fetchProfileInformation(isRefresh)
            .zipWith(fetchUserNewsFeed(isRefresh)) { profile: Profile, list: List<NewsItem> ->
                profile to list
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState(Action.Loading) }
            .subscribe(
                { pair -> updateState(Action.Loaded(pair.first, pair.second)) },
                { throwable -> updateState(Action.Error(throwable)) }
            )
    }

    fun onDeletePost(postId: Int, ownerId: Int) {
        compositeDisposable += removeUserPost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updateState(Action.Remove(postId)) },
                { throwable -> updateState(Action.Error(throwable)) }
            )
    }

    fun onLike(itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) {
        compositeDisposable += likePost(itemId, null, type, canLike, likesCount)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState(Action.Error(throwable))
            }
    }

    fun createPostAndRefresh(message: String) {
        compositeDisposable += createPost(message)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { updateState(Action.Loading) }
            .doOnComplete { getProfileInfo(true) }
            .subscribe(Functions.EMPTY_ACTION) { throwable ->
                updateState(Action.Error(throwable))
            }
    }

    private fun updateState(stateAction: Action) {
        viewState.render(stateMachine.handleUpdate(stateAction))
    }
}
