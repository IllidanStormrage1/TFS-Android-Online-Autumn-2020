package com.example.homework2.presentation.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.presentation.list.PostsAdapter
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.main.FragmentNavigationCallback
import com.example.homework2.presentation.news.UIState
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var activityCallback: FragmentNavigationCallback? = null
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigationCallback) activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostsAdapter(clickCallback = { activityCallback?.navigateToDetail(it) })
        initRecyclerView(adapter)
        observeViewModel(adapter)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> favorites_posts_srl.isRefreshing = it.isLoad
                is UIState.Success -> adapter.submitList(it.payload)
            }
        }
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        favorites_posts_rv.adapter = adapter
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        favorites_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlue),
                adapter
            )
        )
        favorites_posts_srl.setOnRefreshListener { viewModel.onRefresh() }
    }

    companion object {

        fun newInstance() = FavoritesFragment()
    }
}