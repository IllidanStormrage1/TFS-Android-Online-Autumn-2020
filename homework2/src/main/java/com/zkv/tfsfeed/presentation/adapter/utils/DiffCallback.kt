package com.zkv.tfsfeed.presentation.adapter.utils

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.zkv.tfsfeed.domain.model.NewsItem

class DiffCallback : DiffUtil.ItemCallback<NewsItem>() {

    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) = oldItem == newItem

    override fun getChangePayload(oldItem: NewsItem, newItem: NewsItem): Any {
        val diffBundle = Bundle()
        if (oldItem.likesCount != newItem.likesCount) diffBundle.putInt(KEY_LIKES_COUNT,
            newItem.likesCount)
        if (oldItem.canLike != newItem.canLike) diffBundle.putInt(KEY_CAN_LIKE, newItem.canLike)
        if (oldItem.commentsCount != newItem.commentsCount) diffBundle.putInt(KEY_COMMENTS_COUNT,
            newItem.commentsCount)
        if (oldItem.repostsCount != newItem.repostsCount) diffBundle.putInt(KEY_REPOSTS_COUNT,
            newItem.repostsCount)
        if (oldItem.viewsCount != newItem.viewsCount) diffBundle.putString(KEY_VIEWS_COUNT,
            newItem.viewsCount)
        return diffBundle
    }

    companion object {
        const val KEY_LIKES_COUNT = "likesCount"
        const val KEY_CAN_LIKE = "canLike"
        const val KEY_COMMENTS_COUNT = "commentsCount"
        const val KEY_REPOSTS_COUNT = "repostsCount"
        const val KEY_VIEWS_COUNT = "viewsCount"
    }
}