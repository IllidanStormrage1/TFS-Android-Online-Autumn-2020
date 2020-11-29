package com.zkv.tfsfeed.presentation.ui.detail.list.holder

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.presentation.ui.detail.list.CommentDiffCallback
import com.zkv.tfsfeed.presentation.utils.extensions.loadFromUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_comment.*
import kotlinx.android.synthetic.main.merge_comment.view.*

class CommentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(item: Comment) {
        with(item) {
            comment_text_tv.isVisible = text.isNotBlank()
            comment_text_tv.text = text
            comment_content_iv.isVisible = contentUrl != null
            comment_content_iv.loadFromUrl(contentUrl, R.drawable.bg_placeholder)
            comment_avatar_iv.loadFromUrl(avatarUrl, R.drawable.bg_circle_placeholder)
            comment_name_tv.text = nickname
            comment_date.text = date
            comment_likes_tv.text = likesCount.toString()
        }
    }

    fun update(diffBundle: Bundle) {
        diffBundle.keySet().forEach {
            when (it) {
                CommentDiffCallback.KEY_LIKES_COUNT -> itemView.comment_likes_tv.text =
                    diffBundle.getInt(it).toString()
                CommentDiffCallback.KEY_TEXT -> {
                    comment_text_tv.isVisible = !diffBundle.getString(it).isNullOrBlank()
                    itemView.comment_text_tv.text =
                        diffBundle.getString(it)
                }
                CommentDiffCallback.KEY_NICKNAME -> itemView.comment_name_tv.text =
                    diffBundle.getString(it)
                CommentDiffCallback.KEY_AVATAR_URL -> itemView.comment_avatar_iv.loadFromUrl(
                    diffBundle.getString(it),
                    R.drawable.bg_circle_placeholder)
                CommentDiffCallback.KEY_CONTENT_URL -> {
                    itemView.comment_content_iv.isVisible = diffBundle.getString(it) != null
                    itemView.comment_content_iv.loadFromUrl(
                        diffBundle.getString(it),
                        R.drawable.bg_placeholder
                    )
                }
            }
        }
    }
}