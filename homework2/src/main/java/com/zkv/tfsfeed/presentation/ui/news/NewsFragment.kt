package com.zkv.tfsfeed.presentation.ui.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.base.BaseFragment
import com.zkv.tfsfeed.presentation.extensions.showIfNotVisible
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fresh_new_items_extended_fab.*
import kotlinx.android.synthetic.main.placeholder_empty_list.*
import kotlinx.android.synthetic.main.placeholder_shimmer_rc.*

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }
    private var _activityCallback: MainActivityCallback? = null
    private val activityCallback: MainActivityCallback get() = _activityCallback!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityCallback) _activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(
            onIgnore = viewModel::ignoreItem,
            onLike = viewModel::like,
            onClick = activityCallback::navigateToDetail,
            onShare = activityCallback::shareNewsItem)
        initViewState(adapter)
        observeViewModel(adapter)
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        viewModel.newsViewStateLiveData.observe(viewLifecycleOwner) { applyViewState(it, adapter) }
    }

    private fun initViewState(adapter: PostsAdapter) {
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        news_posts_rv.run {
            addItemDecoration(
                DividerItemDecoration(
                    verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                    headerTextSize = resources.getDimension(R.dimen.header_text_size),
                    textColor = ContextCompat.getColor(requireContext(), R.color.colorAccent),
                    callback = adapter
                )
            )
            this.adapter = adapter
        }
        ItemTouchHelper(MainItemTouchHelper(adapter)).run { attachToRecyclerView(news_posts_rv) }
        news_posts_srl.run {
            setOnRefreshListener { loadData() }
            setColorSchemeColors(ContextCompat.getColor(requireContext(),
                R.color.colorAccent))
            setProgressViewOffset(true, -100, 100)
        }
        news_fresh_fab.setOnClickListener {
            news_fresh_fab.hide()
            loadData()
        }
    }

    private fun applyViewState(state: NewsViewState, adapter: PostsAdapter) {
        with(state) {
            news_posts_srl.isRefreshing = showLoading
            news_shimmer.isVisible = showEmptyLoading
            placeholder_list_tv.isVisible = showEmptyError
            empty_placeholder_list_tv.isVisible = showEmptyLoaded
            if (freshItemsAvailable)
                news_fresh_fab.show()
            else
                news_fresh_fab.hide()
            adapter.submitList(news)
            if (showError)
                ErrorDialogFragment.newInstance(errorMessage)
                    .showIfNotVisible(requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY)
        }
    }

    private fun loadData(isRefresh: Boolean = true) {
        news_posts_srl.isRefreshing = true
        viewModel.loadData(isRefresh, System.currentTimeMillis())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _activityCallback = null
    }

    companion object {

        fun newInstance() = NewsFragment()
    }
}