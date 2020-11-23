package com.zkv.tfsfeed.presentation.ui.detail.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.presentation.extensions.loadFromUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_comment.*

class CommentViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(item: Comment) {
        with(item) {
            comment_avatar_iv.loadFromUrl(avatarUrl, R.drawable.bg_circle_placeholder)
            comment_name_tv.text = nickname
            comment_text_tv.text = text
            comment_date.text = date
            comment_likes_tv.text = likesCount.toString()
        }
    }
}