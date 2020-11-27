package com.zkv.tfsfeed.presentation.ui.detail.list

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Comment
import com.zkv.tfsfeed.presentation.ui.detail.list.holder.CommentViewHolder
import com.zkv.tfsfeed.presentation.utils.extensions.inflate

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
        if (payloads.isEmpty())
            onBindViewHolder(holder, position)
        else
            holder.update(payloads[0] as Bundle)
    }

    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
        super.setStateRestorationPolicy(StateRestorationPolicy.PREVENT_WHEN_EMPTY)
    }
}