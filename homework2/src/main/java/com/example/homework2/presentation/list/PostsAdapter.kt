package com.example.homework2.presentation.list

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.domain.model.Post
import com.example.homework2.domain.utils.clearAndAddAll
import com.example.homework2.presentation.list.holder.TextImageViewHolder
import com.example.homework2.presentation.list.holder.TextViewHolder
import com.example.homework2.presentation.list.utils.DiffCallback
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import com.example.homework2.presentation.view.inflate
import java.util.concurrent.Executors

private const val TEXT_HOLDER_TYPE = 0
private const val TEXT_IMAGE_HOLDER_TYPE = 1

class PostsAdapter(
    private val likeCallback: ((item: Post) -> Unit)? = null,
    private val clickCallback: ((item: Post) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    MainItemTouchHelper.ItemTouchHelperAdapter, DividerItemDecoration.DividerAdapterCallback {

    private val currentList = mutableListOf<Post>()

    private val executorService = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper()) {
        val messageObject = it.obj as Pair<DiffUtil.DiffResult, List<Post>>
        currentList.clearAndAddAll(messageObject.second)
        messageObject.first.dispatchUpdatesTo(this)
        return@Handler true
    }

    fun submitList(newItems: List<Post>) {
        executorService.execute {
            val diffCallback = DiffCallback(currentList, newItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback, false)
            handler.sendMessage(Message().apply { obj = diffResult to newItems })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT_IMAGE_HOLDER_TYPE -> TextImageViewHolder(parent.inflate(R.layout.item_post),
                isImage = true)
            TEXT_HOLDER_TYPE -> TextViewHolder(parent.inflate(R.layout.item_post), isImage = false)
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
        likeCallback?.invoke(currentList[position])
        notifyItemChanged(position)
    }

    override fun getData(): List<Post> = currentList

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { clickCallback?.invoke(currentList[holder.absoluteAdapterPosition]) }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}