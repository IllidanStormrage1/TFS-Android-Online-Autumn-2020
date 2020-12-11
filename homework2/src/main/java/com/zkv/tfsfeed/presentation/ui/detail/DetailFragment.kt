package com.zkv.tfsfeed.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ConcatAdapter
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.utils.SpacingItemDecoration
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import com.zkv.tfsfeed.presentation.ui.detail.list.CommentsAdapter
import com.zkv.tfsfeed.presentation.ui.detail.list.HeaderPostAdapter
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.utils.extensions.downloadImageWithExternalStorage
import com.zkv.tfsfeed.presentation.utils.extensions.downloadImageWithMediaStore
import com.zkv.tfsfeed.presentation.utils.extensions.hideKeyboardFrom
import com.zkv.tfsfeed.presentation.utils.extensions.isQHigher
import com.zkv.tfsfeed.presentation.utils.extensions.setOnDebounceClickListener
import com.zkv.tfsfeed.presentation.utils.extensions.showIfNotVisible
import com.zkv.tfsfeed.presentation.utils.extensions.withArgs
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.merge_input_comment.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class DetailFragment : MvpAppCompatFragment(R.layout.fragment_detail), DetailView {

    @Inject
    lateinit var presenterProvider: Provider<DetailPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private val activityCallback get() = requireActivity() as MainActivityCallback

    private lateinit var postsAdapter: HeaderPostAdapter
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var newsItem: NewsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireView() as ViewGroup).layoutTransition.setAnimateParentHierarchy(false)
        postsAdapter = HeaderPostAdapter(
            shareClickHandler = activityCallback::shareNewsItem,
            downloadClickHandler = this::downloadImage
        )
        requireArguments().getParcelable<NewsItem>(KEY_ITEM)?.let {
            postsAdapter.submit(it)
            newsItem = it
            if (it.canPost) {
                presenter.getComments(it.id, it.sourceId)
            } else {
                detail_posts_srl.isEnabled = false
                detail_comment_et.isEnabled = false
                detail_comment_et.setText(resources.getString(R.string.label_comment_disabled))
            }
        }
        commentsAdapter = CommentsAdapter()
        val adapter = ConcatAdapter(postsAdapter, commentsAdapter)
        initViewState(adapter)
    }

    override fun render(state: DetailViewState) {
        with(state) {
            detail_posts_srl.isRefreshing = showLoading
            commentsAdapter.submitList(comments)
            if (showError)
                ErrorDialogFragment.newInstance(errorMessage)
                    .showIfNotVisible(
                        requireActivity().supportFragmentManager,
                        ErrorDialogFragment.ERROR_MESSAGE_KEY
                    )
        }
    }

    private fun initViewState(adapter: ConcatAdapter) {
        detail_posts_rv.run {
            addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.default_margin)))
            this.adapter = adapter
        }
        detail_posts_srl.run {
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            setProgressViewOffset(true, -100, 100)
            setOnRefreshListener { presenter.getComments(newsItem.id, newsItem.sourceId) }
        }
        detail_comment_et.addTextChangedListener(
            onTextChanged = { charSequence: CharSequence?, _: Int, _: Int, _: Int ->
                detail_send_iv.isEnabled = !charSequence.isNullOrBlank()
            }
        )
        detail_send_iv.run {
            isEnabled = false
            setOnDebounceClickListener {
                presenter.createCommentAndRefresh(
                    newsItem.id,
                    newsItem.sourceId,
                    detail_comment_et.text.toString()
                )
                close()
            }
        }
    }

    private fun close() {
        detail_comment_et.text.clear()
        requireContext().hideKeyboardFrom(detail_comment_et)
    }

    private fun downloadImage(imageView: ImageView) {
        val drawable = imageView.drawable
        if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
            val bitmap = drawable.toBitmap()
            if (isQHigher)
                requireContext().downloadImageWithMediaStore(bitmap)
            else
                requireActivity().downloadImageWithExternalStorage(bitmap)
        }
    }

    companion object {
        const val KEY_ITEM = "item"

        fun newInstance(item: NewsItem) = DetailFragment().withArgs {
            putParcelable(KEY_ITEM, item)
        }
    }
}
