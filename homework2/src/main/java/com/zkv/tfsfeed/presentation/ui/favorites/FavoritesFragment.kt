package com.zkv.tfsfeed.presentation.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.base.BaseFragment
import com.zkv.tfsfeed.presentation.extensions.showIfNotVisible
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsViewState
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.placeholder_empty_list.*
import kotlinx.android.synthetic.main.placeholder_shimmer_rc.*

class FavoritesFragment : BaseFragment(R.layout.fragment_news) {

    private val viewModel: FavoritesViewModel by viewModels { viewModelFactory }
    private var _activityCallback: MainActivityCallback? = null
    private val activityCallback: MainActivityCallback get() = _activityCallback!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityCallback) _activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(
            onClick = activityCallback::navigateToDetail,
            onShare = activityCallback::shareNewsItem)
        initRecyclerView(adapter)
        observeViewModel(adapter)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        viewModel.favoritesViewStateLiveData.observe(viewLifecycleOwner) {
            applyViewState(it, adapter)
        }
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        news_posts_rv.adapter = adapter
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        news_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(requireContext(), R.color.colorAccent),
                adapter
            )
        )
        news_posts_srl.setOnRefreshListener { viewModel.onRefresh(true) }
        news_posts_srl.setColorSchemeColors(ContextCompat.getColor(requireContext(),
            R.color.colorAccent))
    }

    private fun applyViewState(state: NewsViewState, adapter: PostsAdapter) {
        with(state) {
            news_posts_srl.isRefreshing = showLoading
            news_shimmer.isVisible = showEmptyLoading
            placeholder_list_tv.isVisible = showEmptyError
            empty_placeholder_list_tv.isVisible = showEmptyLoaded
            adapter.submitList(news)
            if (showError)
                ErrorDialogFragment.newInstance(errorMessage)
                    .showIfNotVisible(requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _activityCallback = null
    }

    companion object {

        fun newInstance() = FavoritesFragment()
    }
}