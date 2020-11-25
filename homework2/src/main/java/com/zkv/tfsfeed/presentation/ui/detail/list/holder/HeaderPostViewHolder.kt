package com.zkv.tfsfeed.presentation.ui.detail.list.holder

import android.text.util.Linkify
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.extensions.loadFromUrl
import com.zkv.tfsfeed.presentation.widget.SocialPostLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_item_post.*

class HeaderPostViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        (itemView as SocialPostLayout).isAvailableToDownload = true
    }

    fun bind(item: NewsItem) {
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
            views_tv.text = viewsCount
            avatar_iv.loadFromUrl(avatarUrl, R.drawable.bg_placeholder)
            if (item.photoUrl == null) {
                content_iv.isVisible = false
            } else {
                content_iv.adjustViewBounds = true
                content_iv.updateLayoutParams { height = ViewGroup.LayoutParams.MATCH_PARENT }
                content_iv.loadFromUrl(
                    photoUrl,
                    R.drawable.bg_placeholder)
            }
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }
}