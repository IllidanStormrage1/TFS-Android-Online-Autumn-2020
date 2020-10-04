package com.example.homework2.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.domain.Post
import com.example.homework2.presentation.list.holder.TextImageViewHolder
import com.example.homework2.presentation.list.holder.TextViewHolder
import com.example.homework2.presentation.list.utils.DiffCallback
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import com.example.homework2.presentation.view.SocialPostLayout

const val TEXT_HOLDER_TYPE = 0
const val TEXT_IMAGE_HOLDER_TYPE = 1

class PostsAdapter(private val callback: (id: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    MainItemTouchHelper.ItemTouchHelperAdapter, DividerItemDecoration.DividerAdapterCallback {

    private val currentList = mutableListOf<Post>()

    fun submitList(newItems: List<Post>) {
        val diffCallback = DiffCallback(currentList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        currentList.clear()
        currentList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT_IMAGE_HOLDER_TYPE ->
                TextImageViewHolder(
                    SocialPostLayout(
                        isImage = true,
                        context = parent.context,
                    )
                )
            TEXT_HOLDER_TYPE ->
                TextViewHolder(
                    SocialPostLayout(
                        isImage = false,
                        context = parent.context
                    )
                )
            else -> throw IllegalStateException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind(currentList[position])
            is TextImageViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].photoUrl?.let { TEXT_IMAGE_HOLDER_TYPE } ?: TEXT_HOLDER_TYPE

    override fun getItemCount(): Int = currentList.size

    override fun onItemSwipedStart(position: Int) {
        currentList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemSwipedEnd(position: Int) {
        callback(currentList[position].id)
        notifyItemChanged(position)
    }

    override fun getData(): List<Post> = currentList
}