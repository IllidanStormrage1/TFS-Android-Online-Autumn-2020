package com.example.homework2.presentation.ui.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.homework2.R
import com.example.homework2.presentation.list.PostsAdapter
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import com.example.homework2.presentation.ui.main.FragmentNavigationCallback
import com.example.homework2.presentation.view.isStarting
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.placeholder_empty_list.*
import kotlinx.android.synthetic.main.placeholder_shimmer_rc.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var activityCallback: FragmentNavigationCallback
    private val viewModel: NewsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigationCallback) activityCallback = context
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
        viewModel.contentLiveData.observe(viewLifecycleOwner) { state ->
            applyViewState(state, adapter)
        }
        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { changeNavigationItem(it) }
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        news_posts_rv.adapter = adapter
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        news_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlue),
                adapter
            )
        )
        ItemTouchHelper(MainItemTouchHelper(adapter)).also { it.attachToRecyclerView(news_posts_rv) }
        news_posts_srl.setOnRefreshListener { viewModel.loadData() }
    }

    private fun applyViewState(state: UIState, adapter: PostsAdapter) {
        when (state) {
            is UIState.Loading -> {
                if (news_posts_srl.isRefreshing)
                    news_posts_srl.isRefreshing = state.isLoad
                else
                    news_shimmer.isStarting = state.isLoad
            }
            is UIState.Success -> {
                news_shimmer.isStarting = false
                news_posts_srl.isRefreshing = false
                placeholder_list.isVisible = false
                adapter.submitList(state.payload)
            }
            is UIState.Failure -> {
                if (news_posts_srl.isRefreshing) {
                    news_posts_srl.isRefreshing = false
                    activityCallback.showErrorDialog(state.throwable)
                } else {
                    news_shimmer.isStarting = false
                    placeholder_list.isVisible = true
                }
            }
        }
    }

    private fun changeNavigationItem(isVisible: Boolean) {
        activityCallback.setVisibleFavoritesItem(isVisible)
    }

    companion object {

        fun newInstance() = NewsFragment()
    }
}