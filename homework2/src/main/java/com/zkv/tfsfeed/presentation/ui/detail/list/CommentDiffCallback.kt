package com.zkv.tfsfeed.presentation.ui.detail.list

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.zkv.tfsfeed.domain.model.Comment

class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Comment, newItem: Comment): Any {
        val diffBundle = Bundle()
        if (oldItem.likesCount != newItem.likesCount) diffBundle.putInt(KEY_LIKES_COUNT,
            newItem.likesCount)
        if (oldItem.text != newItem.text) diffBundle.putString(KEY_TEXT, newItem.text)
        if (oldItem.nickname != newItem.nickname) diffBundle.putString(KEY_NICKNAME,
            newItem.nickname)
        if (oldItem.avatarUrl != newItem.avatarUrl) diffBundle.putString(KEY_AVATAR_URL,
            newItem.avatarUrl)
        if (oldItem.contentUrl != newItem.contentUrl) diffBundle.putString(KEY_CONTENT_URL,
            newItem.contentUrl)
        return diffBundle
    }

    companion object {
        const val KEY_LIKES_COUNT = "likesCount"
        const val KEY_TEXT = "text"
        const val KEY_NICKNAME = "nickname"
        const val KEY_AVATAR_URL = "avatarUrl"
        const val KEY_CONTENT_URL = "contentUrl"
    }
}
