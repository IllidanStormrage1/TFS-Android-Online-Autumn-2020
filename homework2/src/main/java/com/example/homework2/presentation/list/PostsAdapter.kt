package com.example.homework2.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.domain.Post
import com.example.homework2.presentation.list.holder.TextImageViewHolder
import com.example.homework2.presentation.list.holder.TextViewHolder
import com.example.homework2.presentation.list.utils.DiffCallback
import com.example.homework2.presentation.list.utils.DividerAdapterCallback
import com.example.homework2.presentation.list.utils.ItemTouchHelperAdapter
import com.example.homework2.presentation.view.SocialPostLayout

const val TEXT_HOLDER_TYPE = 0
const val TEXT_IMAGE_HOLDER_TYPE = 1

class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemTouchHelperAdapter, DividerAdapterCallback {

    private val differ = AsyncListDiffer(this, DiffCallback())

    fun submitList(list: List<Post>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val lp = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return when (viewType) {
            TEXT_IMAGE_HOLDER_TYPE -> {
                val view = SocialPostLayout(true, parent.context)
                view.layoutParams = lp
                TextImageViewHolder(view)
            }
            TEXT_HOLDER_TYPE -> {
                val view = SocialPostLayout(context = parent.context)
                view.layoutParams = lp
                TextViewHolder(view)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> holder.bind(differ.currentList[position])
            is TextImageViewHolder -> holder.bind(differ.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (differ.currentList[position].photosUrl != null) {
            TEXT_IMAGE_HOLDER_TYPE
        } else {
            TEXT_HOLDER_TYPE
        }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onItemSwipedLeft(position: Int) {
        val editedList = differ.currentList.toMutableList().also { it.removeAt(position) }
        differ.submitList(editedList)
    }

    override fun onItemSwipedRight(position: Int) {
        differ.currentList[position].likesCount++
        notifyItemChanged(position)
    }

    override fun getData(position: Int): Int = differ.currentList[position].date
}