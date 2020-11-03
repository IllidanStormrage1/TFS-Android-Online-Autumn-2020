package com.zkv.tfsfeed.presentation.ui.detail

import android.content.Context
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.extensions.*
import com.zkv.tfsfeed.presentation.ui.MainActivityCallback
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.merge_item_post.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _activityCallback: MainActivityCallback? = null
    private val activityCallback: MainActivityCallback get() = _activityCallback!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityCallback) _activityCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().getParcelable<NewsItem>(KEY_ITEM)?.let(::initView)
    }

    private fun initView(item: NewsItem) {
        with(item) {
            content_tv.autoLinkMask = Linkify.ALL
            content_tv.setTextIsSelectable(true)
            content_tv.linksClickable = true
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            like_btn.text = likesCount.toString()
            comment_btn.text = commentsCount.toString()
            share_btn.text = repostsCount.toString()
            share_btn.setOnClickListener { activityCallback.shareNewsItem(this) }
            views_tv.text = viewsCount
            avatar_iv.loadFromUrl(avatarUrl, R.drawable.background_placeholder)
            if (item.photoUrl == null) {
                content_iv.isVisible = false
            } else {
                content_iv.adjustViewBounds = true
                content_iv.updateLayoutParams { height = MATCH_PARENT }
                content_iv.loadFromUrl(
                    photoUrl,
                    R.drawable.background_placeholder)
                detail_save_btn.isVisible = true
                detail_save_btn.setOnClickListener { downloadImage(content_iv) }
            }
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }

    private fun downloadImage(imageView: ImageView) {
        val drawable = imageView.drawable
        if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
            val bitmap = drawable.toBitmap()
            if (isQHigher)
                requireContext().downloadImageWithMediaStore(bitmap)
            else {
                requireActivity().downloadImageWithExternalStorage(bitmap)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _activityCallback = null
    }

    companion object {

        const val KEY_ITEM = "item"

        fun newInstance(item: NewsItem) = DetailFragment().withArgs {
            putParcelable(KEY_ITEM, item)
        }
    }
}