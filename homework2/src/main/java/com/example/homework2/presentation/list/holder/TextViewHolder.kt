package com.example.homework2.presentation.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*
import com.example.homework2.domain.Post

class TextViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Post) {
        group_name_tv.text = item.displayName
        post_creation_date_tv.text = item.date.toString()
        content_tv.text = item.text
        like_btn.text = item.likesCount.toString()
        comment_btn.text = item.commentsCount.toString()
        share_btn.text = item.repostsCount.toString()
        Glide.with(containerView.context).load(item.avatarUrl).into(avatar_iv)
    }
}