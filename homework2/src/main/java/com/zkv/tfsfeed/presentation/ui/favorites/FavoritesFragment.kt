package com.zkv.tfsfeed.presentation.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.extensions.showIfNotVisible
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsViewState
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fresh_new_items_extended_fab.*
import kotlinx.android.synthetic.main.placeholder_empty_list.*
import kotlinx.android.synthetic.main.placeholder_shimmer_rc.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import kotlin.properties.Delegates

class FavoritesFragment : MvpAppCompatFragment(R.layout.fragment_news), FavoritesView {

    @Inject
    lateinit var presenterProvider: Provider<FavoritesPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var _activityCallback: MainActivityCallback? = null
    private val activityCallback: MainActivityCallback get() = _activityCallback!!

    private var adapter: PostsAdapter by Delegates.notNull()

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
        (view as ViewGroup).layoutTransition.setAnimateParentHierarchy(false)
        adapter = PostsAdapter(
            onClick = activityCallback::navigateToDetail,
            onShare = activityCallback::shareNewsItem)
        initRecyclerView(adapter)
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        news_posts_rv.adapter = adapter
        news_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(requireContext(), R.color.colorAccent),
                adapter
            )
        )
        news_posts_srl.setOnRefreshListener { presenter.onRefresh(true) }
        news_posts_srl.setColorSchemeColors(ContextCompat.getColor(requireContext(),
            R.color.colorAccent))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _activityCallback = null
    }

    override fun render(state: NewsViewState) {
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

    companion object {

        fun newInstance() = FavoritesFragment()
    }
}