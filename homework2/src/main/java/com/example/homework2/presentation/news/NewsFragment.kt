package com.example.homework2.presentation.news

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.homework2.R
import com.example.homework2.domain.Post
import com.example.homework2.presentation.detail.DetailFragment
import com.example.homework2.presentation.list.PostsAdapter
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(object : PostsAdapter.AdapterCallback {
            override fun like(id: Int) = viewModel.like(id)
            override fun onClick(item: Post) = navigateToDetail(item)
        })
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

    private fun navigateToDetail(item: Post) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(android.R.id.content, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        fun newInstance() = NewsFragment()
    }
}