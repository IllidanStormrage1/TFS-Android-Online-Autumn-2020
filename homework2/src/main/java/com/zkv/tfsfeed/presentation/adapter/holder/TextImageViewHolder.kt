package com.zkv.tfsfeed.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.loadFromUrlWithCrop
import com.zkv.tfsfeed.presentation.view.SocialPostLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_item_post.*

class TextImageViewHolder(override val containerView: View, isImage: Boolean) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        (itemView as SocialPostLayout).isImage = isImage
    }

    fun bind(item: NewsItem) {
        item.run {
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            like_btn.text = likesCount.toString()
            comment_btn.text = commentsCount.toString()
            share_btn.text = repostsCount.toString()
            views_tv.text = viewsCount
            content_iv.loadFromUrlWithCrop(photoUrl, R.drawable.rounded_background_placeholder)
            avatar_iv.loadFromUrlWithCrop(avatarUrl, R.drawable.avatar_placeholder)
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }
}