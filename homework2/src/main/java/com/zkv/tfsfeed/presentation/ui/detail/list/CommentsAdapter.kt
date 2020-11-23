package com.zkv.tfsfeed.presentation.ui.detail.list

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.presentation.extensions.inflate
import com.zkv.tfsfeed.presentation.extensions.loadFromUrl
import com.zkv.tfsfeed.presentation.ui.detail.list.holder.CommentViewHolder
import kotlinx.android.synthetic.main.merge_comment.view.*

class CommentsAdapter : ListAdapter<Comment, CommentViewHolder>(CommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =
        CommentViewHolder(parent.inflate(R.layout.item_rc_comment))

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: CommentViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val diffBundle = payloads[0] as Bundle
            diffBundle.keySet().forEach {
                when (it) {
                    CommentDiffCallback.KEY_LIKES_COUNT -> holder.itemView.comment_likes_tv.text =
                        diffBundle.getInt(it).toString()
                    CommentDiffCallback.KEY_TEXT -> holder.itemView.comment_text_tv.text =
                        diffBundle.getString(it)
                    CommentDiffCallback.KEY_NICKNAME -> holder.itemView.comment_name_tv.text =
                        diffBundle.getString(it)
                    CommentDiffCallback.KEY_AVATAR_URL -> holder.itemView.comment_avatar_iv.loadFromUrl(
                        diffBundle.getString(it),
                        R.drawable.bg_circle_placeholder)
                }
            }
        }
    }
}