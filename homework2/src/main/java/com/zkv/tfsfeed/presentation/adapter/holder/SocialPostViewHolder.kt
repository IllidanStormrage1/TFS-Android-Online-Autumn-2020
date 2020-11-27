package com.zkv.tfsfeed.presentation.adapter.holder

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback
import com.zkv.tfsfeed.presentation.utils.extensions.loadFromUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_item_post.*
import kotlinx.android.synthetic.main.merge_item_post.view.*

class SocialPostViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: NewsItem) {
        item.run {
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            comment_btn.isEnabled = canPost
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
            content_iv.isVisible = contentUrl != null
            content_iv.loadFromUrl(contentUrl,
                R.drawable.bg_placeholder,
                RequestOptions().centerCrop())
            avatar_iv.loadFromUrl(avatarUrl,
                R.drawable.bg_placeholder,
                RequestOptions().centerCrop())
            if (canLike == 0)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }

    fun update(diffBundle: Bundle) {
        diffBundle.keySet().forEach {
            when (it) {
                DiffCallback.KEY_LIKES_COUNT -> itemView.like_btn.text =
                    if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                DiffCallback.KEY_CAN_LIKE -> {
                    if (diffBundle.getInt(it) == 0)
                        itemView.like_btn.setIconResource(R.drawable.ic_heart_selected)
                    else
                        itemView.like_btn.setIconResource(R.drawable.ic_heart)
                }
                DiffCallback.KEY_COMMENTS_COUNT -> itemView.comment_btn.text =
                    if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                DiffCallback.KEY_REPOSTS_COUNT -> itemView.share_btn.text =
                    if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                DiffCallback.KEY_VIEWS_COUNT -> itemView.views_tv.text = diffBundle.getString(it)
                DiffCallback.KEY_TEXT -> itemView.content_tv.text = diffBundle.getString(it)
            }
        }
    }
}