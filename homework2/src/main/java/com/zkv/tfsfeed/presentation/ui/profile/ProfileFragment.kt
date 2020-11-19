package com.zkv.tfsfeed.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.adapter.utils.SpacingItemDecoration
import com.zkv.tfsfeed.presentation.extensions.showIfNotVisible
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.creator.CreatorPostFragment
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.profile.header.HeaderAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.plc_error_list_tv.*
import kotlinx.android.synthetic.main.plc_shimmer_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import kotlin.properties.Delegates

class ProfileFragment : MvpAppCompatFragment(R.layout.fragment_profile), ProfileView {

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var _activityCallback: MainActivityCallback? = null
    private val activityCallback: MainActivityCallback get() = _activityCallback!!

    private var headerAdapter: HeaderAdapter by Delegates.notNull()
    private var postsAdapter: PostsAdapter by Delegates.notNull()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityCallback) _activityCallback = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireView() as ViewGroup).layoutTransition.setAnimateParentHierarchy(false)
        headerAdapter = HeaderAdapter(clicksHandler = activityCallback::navigateToCreatorPost)
        postsAdapter = PostsAdapter(
            onLikeHandler = presenter::onLike,
            onIgnoreHandler = presenter::onDeletePost,
            onClickHandler = activityCallback::navigateToDetail,
            onShareHandler = activityCallback::shareNewsItem)
        val adapter = ConcatAdapter(headerAdapter, postsAdapter)
        initViewState(adapter)
        setFragmentResultListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _activityCallback = null
    }

    override fun render(state: ProfileViewState) {
        with(state) {
            profile_posts_srl.isRefreshing = showLoading
            news_shimmer.isVisible = showEmptyLoading
            placeholder_empty_error_tv.isVisible = showEmptyError
            placeholder_empty_error_tv.text = errorMessage
            profile?.let(headerAdapter::submit)
            postsAdapter.submitList(news)
            if (showError)
                ErrorDialogFragment.newInstance(errorMessage)
                    .showIfNotVisible(requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY)
        }
    }

    private fun initViewState(adapter: ConcatAdapter) {
        profile_posts_rv.run {
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
        requireActivity().supportFragmentManager.setFragmentResultListener(CreatorPostFragment.RESULT_REQUEST_KEY,
            viewLifecycleOwner) { key: String, bundle: Bundle ->
            presenter.createPostAndRefresh(bundle.getString(key, null))
        }
    }

    companion object {

        fun newInstance() = ProfileFragment()
    }
}