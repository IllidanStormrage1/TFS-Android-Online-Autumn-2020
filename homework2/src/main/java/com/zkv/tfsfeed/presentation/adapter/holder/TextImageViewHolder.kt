package com.zkv.tfsfeed.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.extensions.loadFromUrl
import com.zkv.tfsfeed.presentation.widget.SocialPostLayout
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
            if (likesCount != 0)
                like_btn.text = likesCount.toString()
            else
                like_btn.text = ""
            if (commentsCount != 0)
                comment_btn.text = commentsCount.toString()
            else
                comment_btn.text = ""
            if (repostsCount != 0)
                share_btn.text = repostsCount.toString()
            else
                share_btn.text = ""
            views_tv.text = viewsCount
            content_iv.loadFromUrl(photoUrl,
                R.drawable.background_placeholder,
                RequestOptions().centerCrop())
            avatar_iv.loadFromUrl(avatarUrl,
                R.drawable.background_placeholder,
                RequestOptions().centerCrop())
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }
}