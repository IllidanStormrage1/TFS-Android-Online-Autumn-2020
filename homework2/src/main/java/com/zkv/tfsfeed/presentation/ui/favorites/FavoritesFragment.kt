package com.zkv.tfsfeed.presentation.ui.favorites

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.PostsAdapter
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.news.Event
import com.zkv.tfsfeed.presentation.ui.news.NewsViewState
import com.zkv.tfsfeed.presentation.utils.extensions.isLaunched
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.plc_empty_list.*
import kotlinx.android.synthetic.main.plc_error_list_tv.*
import kotlinx.android.synthetic.main.plc_shimmer_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class FavoritesFragment : MvpAppCompatFragment(R.layout.fragment_news), FavoritesView {

    @Inject
    lateinit var presenterProvider: Provider<FavoritesPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private val activityCallback get() = requireActivity() as MainActivityCallback

    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireView() as ViewGroup).layoutTransition.setAnimateParentHierarchy(false)
        adapter = PostsAdapter(
            onClickHandler = presenter::navigateToDetail,
            onShareHandler = activityCallback::shareNewsItem
        )
        initViewState(adapter)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData(isRefresh = false)
    }

    override fun render(state: NewsViewState) {
        with(state) {
            news_posts_srl.isRefreshing = showLoading
            news_shimmer.isLaunched = showEmptyLoading
            placeholder_empty_error_tv.isVisible = showEmptyError
            empty_placeholder_list_tv.isVisible = showEmptyLoaded
            adapter.submitList(news)
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

    private fun initViewState(adapter: PostsAdapter) {
        news_posts_rv.run {
            setHasFixedSize(true)
            this.adapter = adapter
            addItemDecoration(
                DividerItemDecoration(
                    verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                    headerTextSize = resources.getDimension(R.dimen.header_text_size),
                    textColor = ContextCompat.getColor(requireContext(), R.color.colorAccent),
                    adapter
                )
            )
        }
        news_posts_srl.run {
            setOnRefreshListener { presenter.loadData(true) }
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            setProgressViewOffset(true, -100, 100)
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}
