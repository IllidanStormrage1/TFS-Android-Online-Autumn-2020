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
import com.zkv.tfsfeed.presentation.MainActivityCallback
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.base.BaseFragment
import com.zkv.tfsfeed.presentation.isStarting
import com.zkv.tfsfeed.presentation.showIfNotVisible
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.placeholder_empty_list.*
import kotlinx.android.synthetic.main.placeholder_shimmer_rc.*

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }
    private lateinit var activityCallback: MainActivityCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityCallback) activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(
            likeCallback = { viewModel.like(it) },
            clickCallback = { activityCallback.navigateToDetail(it) })
        initRecyclerView(adapter)
        observeViewModel(adapter)
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        viewModel.newsViewStateLiveData.observe(viewLifecycleOwner) { applyViewState(it, adapter) }
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        news_posts_rv.adapter = adapter
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        news_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(requireContext(), R.color.steel_blue),
                adapter
            )
        )
        ItemTouchHelper(MainItemTouchHelper(adapter)).also { it.attachToRecyclerView(news_posts_rv) }
        news_posts_srl.setOnRefreshListener { viewModel.loadData() }
        news_posts_srl.setColorSchemeColors(ContextCompat.getColor(requireContext(),
            R.color.colorAccent))
    }

    private fun applyViewState(state: NewsViewState, adapter: PostsAdapter) {
        with(state) {
            news_posts_srl.isRefreshing = showLoading
            news_shimmer.isStarting = showEmptyLoading
            placeholder_list_tv.isVisible = showEmptyError
            empty_placeholder_list_tv.isVisible = showEmptyLoaded
            adapter.submitList(news)
            if (showError)
                ErrorDialogFragment.newInstance(errorMessage)
                    .showIfNotVisible(requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY)
        }
    }

    companion object {

        fun newInstance() = NewsFragment()
    }
}