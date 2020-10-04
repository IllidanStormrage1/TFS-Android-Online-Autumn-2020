package com.example.homework2.presentation.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.domain.Post
import com.example.homework2.presentation.view.loadFromUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*

class TextViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Post) {
        item.run {
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            like_btn.text = likesCount.toString()
            comment_btn.text = commentsCount.toString()
            share_btn.text = repostsCount.toString()
            content_iv.loadFromUrl(photoUrl, R.drawable.image_placeholder)
            avatar_iv.loadFromUrl(avatarUrl, R.drawable.avatar_placeholder)
            if (isFavorite)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }
}