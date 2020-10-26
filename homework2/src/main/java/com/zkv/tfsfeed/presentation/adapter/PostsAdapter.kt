package com.zkv.tfsfeed.presentation.adapter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.clearAndAddAll
import com.zkv.tfsfeed.presentation.adapter.holder.TextImageViewHolder
import com.zkv.tfsfeed.presentation.adapter.holder.TextViewHolder
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_CAN_LIKE
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_COMMENTS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_LIKES_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_REPOSTS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_VIEWS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.inflate
import kotlinx.android.synthetic.main.merge_item_post.view.*
import java.util.concurrent.Executors

private const val TEXT_HOLDER_TYPE = 0
private const val TEXT_IMAGE_HOLDER_TYPE = 1

class PostsAdapter(
    private inline val likeCallback: ((item: NewsItem) -> Unit)? = null,
    private inline val clickCallback: ((item: NewsItem) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    MainItemTouchHelper.ItemTouchHelperAdapter, DividerItemDecoration.DividerAdapterCallback {

    private val currentList = mutableListOf<NewsItem>()

    private val executorService = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper()) {
        val messageObject = it.obj as Pair<DiffUtil.DiffResult, List<NewsItem>>
        messageObject.first.dispatchUpdatesTo(this)
        currentList.clearAndAddAll(messageObject.second)
        return@Handler true
    }

    fun submitList(newItems: List<NewsItem>) {
        executorService.execute {
            val diffCallback = DiffCallback(currentList, newItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val diffBundle = payloads[0] as Bundle
            diffBundle.keySet().forEach {
                when (it) {
                    KEY_LIKES_COUNT -> holder.itemView.like_btn.text =
                        diffBundle.getInt(it).toString()
                    KEY_CAN_LIKE -> {
                        if (diffBundle.getInt(it) == 0)
                            holder.itemView.like_btn.setIconResource(R.drawable.ic_heart_selected)
                        else
                            holder.itemView.like_btn.setIconResource(R.drawable.ic_heart)
                    }
                    KEY_COMMENTS_COUNT -> holder.itemView.comment_btn.text =
                        diffBundle.getInt(it).toString()
                    KEY_REPOSTS_COUNT -> holder.itemView.share_btn.text =
                        diffBundle.getInt(it).toString()
                    KEY_VIEWS_COUNT -> holder.itemView.views_tv.text =
                        diffBundle.getInt(it).toString()
                }
            }
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
        val item = currentList[position]
        likeCallback?.invoke(item)
        item.onLike()
        notifyItemChanged(position)
    }

    override fun getData(): List<NewsItem> = currentList

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { clickCallback?.invoke(currentList[holder.absoluteAdapterPosition]) }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}