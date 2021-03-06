package com.zkv.tfsfeed.presentation.ui.profile

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.transition.MaterialFadeThrough
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.adapter.utils.SpacingItemDecoration
import com.zkv.tfsfeed.presentation.navigation.ProfileNavigator
import com.zkv.tfsfeed.presentation.navigation.ProfileRouter
import com.zkv.tfsfeed.presentation.ui.creator.CreatorPostFragment
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.main.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.profile.header.HeaderAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.plc_error_list_tv.*
import kotlinx.android.synthetic.main.plc_shimmer_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : MvpAppCompatFragment(R.layout.fragment_profile), ProfileView {

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var profileNavigator: ProfileNavigator

    private val activityCallback get() = requireActivity() as MainActivityCallback

    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        exitTransition = MaterialFadeThrough()
        profileNavigator.setProfileRouter(ProfileRouter(requireActivity().supportFragmentManager))
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireView() as ViewGroup).layoutTransition.setAnimateParentHierarchy(false)
        headerAdapter = HeaderAdapter(clicksHandler = presenter::navigateToCreatorPost)
        postsAdapter = PostsAdapter(
            onLikeHandler = presenter::onLike,
            onIgnoreHandler = presenter::onDeletePost,
            onClickHandler = presenter::navigateToDetail,
            onShareHandler = activityCallback::shareNewsItem
        )
        val adapter = ConcatAdapter(headerAdapter, postsAdapter)
        initViewState(adapter)
        setFragmentResultListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        profileNavigator.removeRouter()
    }

    override fun render(state: ProfileViewState) {
        with(state) {
            profile_posts_srl.isRefreshing = showLoading
            news_shimmer.isVisible = showEmptyLoading
            placeholder_empty_error_tv.isVisible = showEmptyError
            placeholder_empty_error_tv.text = errorMessage
            profile?.let(headerAdapter::submit)
            postsAdapter.submitList(news)
        }
    }

    override fun renderEvent(event: Event) {
        when (event) {
            is Event.ShowErrorDialog ->
                ErrorDialogFragment.newInstance(event.errorMessage)
                    .show(
                        requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY
                    )
        }
    }

    private fun initViewState(adapter: ConcatAdapter) {
        profile_posts_rv.run {
            setHasFixedSize(true)
            addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.default_margin)))
            this.adapter = adapter
        }
        profile_posts_srl.run {
            setOnRefreshListener { presenter.getProfileInfo(true) }
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            setProgressViewOffset(true, -100, 100)
        }
        ItemTouchHelper(MainItemTouchHelper(postsAdapter)).run {
            attachToRecyclerView(profile_posts_rv)
        }
    }

    private fun setFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            CreatorPostFragment.RESULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { key: String, bundle: Bundle ->
            presenter.createPostAndRefresh(bundle.getString(key, null))
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
