package com.zkv.tfsfeed.presentation.ui.detail

import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.loadFromUrl
import kotlinx.android.synthetic.main.merge_item_post.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<NewsItem>(KEY_ITEM)?.let(::initView)
    }

    private fun initView(item: NewsItem) {
        item.run {
            content_iv.adjustViewBounds = true
            content_iv.updateLayoutParams { height = MATCH_PARENT }
            content_tv.autoLinkMask = Linkify.ALL
            content_tv.setTextIsSelectable(true)
            content_tv.linksClickable = true
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            like_btn.text = likesCount.toString()
            comment_btn.text = commentsCount.toString()
            share_btn.text = repostsCount.toString()
            views_tv.text = viewsCount
            avatar_iv.loadFromUrl(avatarUrl, R.drawable.avatar_placeholder)
            if (item.photoUrl == null)
                content_iv.isVisible = false
            else
                content_iv.loadFromUrl(
                    photoUrl,
                    R.drawable.rounded_background_placeholder)
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }

    companion object {

        const val KEY_ITEM = "item"

        fun newInstance(item: NewsItem) = DetailFragment().apply {
            arguments = bundleOf(KEY_ITEM to item)
        }
    }
}