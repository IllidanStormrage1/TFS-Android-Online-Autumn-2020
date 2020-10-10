package com.example.homework2.presentation.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.homework2.R
import com.example.homework2.presentation.list.PostsAdapter
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import com.example.homework2.presentation.main.FragmentNavigationCallback
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    private var activityCallback: FragmentNavigationCallback? = null
    private val viewModel: NewsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigationCallback) activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(
            likeCallback = { viewModel.like(it) },
            clickCallback = { activityCallback?.navigateToDetail(it) })
        initRecyclerView(adapter)
        observeViewModel(adapter)
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> news_posts_srl.isRefreshing = it.isLoad
                is UIState.Success -> adapter.submitList(it.payload)
            }
        }
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
        news_posts_srl.setOnRefreshListener { viewModel.onRefresh() }
    }

    companion object {

        fun newInstance() = NewsFragment()
    }
}