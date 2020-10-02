package com.example.homework2.presentation.list.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.homework2.domain.Post

class DiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}