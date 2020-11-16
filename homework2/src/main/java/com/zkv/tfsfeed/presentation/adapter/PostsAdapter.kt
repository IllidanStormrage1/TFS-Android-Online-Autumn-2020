package com.zkv.tfsfeed.presentation.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.like
import com.zkv.tfsfeed.presentation.adapter.holder.TextImageViewHolder
import com.zkv.tfsfeed.presentation.adapter.holder.TextViewHolder
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_CAN_LIKE
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_COMMENTS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_LIKES_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_REPOSTS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_TEXT
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback.Companion.KEY_VIEWS_COUNT
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.extensions.inflate
import kotlinx.android.synthetic.main.merge_item_post.view.*

private const val TEXT_HOLDER_TYPE = 0
private const val TEXT_IMAGE_HOLDER_TYPE = 1

private typealias ItemHandler = ((item: NewsItem) -> Unit)?

class PostsAdapter(
    private inline val onIgnore: ((itemId: Int, sourceId: Int) -> Unit)? = null,
    private inline val onLike: ((itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) -> Unit)? = null,
    private inline val onClick: ItemHandler = null,
    private inline val onShare: ItemHandler = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    MainItemTouchHelper.ItemTouchHelperAdapter, DividerItemDecoration.DividerAdapterCallback {

    private val differ = AsyncListDiffer(this, DiffCallback())
    private val currentList: List<NewsItem> get() = differ.currentList

    fun submitList(newItems: List<NewsItem>) {
        differ.submitList(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT_IMAGE_HOLDER_TYPE -> TextImageViewHolder(parent.inflate(R.layout.item_rc_post),
                isImage = true)
            TEXT_HOLDER_TYPE -> TextViewHolder(parent.inflate(R.layout.item_rc_post),
                isImage = false)
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
                        if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                    KEY_CAN_LIKE -> {
                        if (diffBundle.getInt(it) == 0)
                            holder.itemView.like_btn.setIconResource(R.drawable.ic_heart_selected)
                        else
                            holder.itemView.like_btn.setIconResource(R.drawable.ic_heart)
                    }
                    KEY_COMMENTS_COUNT -> holder.itemView.comment_btn.text =
                        if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                    KEY_REPOSTS_COUNT -> holder.itemView.share_btn.text =
                        if (diffBundle.getInt(it) != 0) diffBundle.getInt(it).toString() else ""
                    KEY_VIEWS_COUNT -> holder.itemView.views_tv.text = diffBundle.getString(it)
                    KEY_TEXT -> holder.itemView.content_tv.text = diffBundle.getString(it)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].photoUrl?.let { TEXT_IMAGE_HOLDER_TYPE } ?: TEXT_HOLDER_TYPE

    override fun getItemCount(): Int = currentList.size

    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
        super.setStateRestorationPolicy(StateRestorationPolicy.PREVENT_WHEN_EMPTY)
    }

    override fun onItemSwipedStart(position: Int) {
        val item = currentList[position]
        onIgnore?.invoke(item.id, item.sourceId)
        currentList.toMutableList().also { items ->
            items.removeAt(position)
            submitList(items)
        }
    }

    override fun onItemSwipedEnd(position: Int) {
        currentList[position].also { item ->
            onLike?.invoke(item.id, item.sourceId, item.type, item.canLike, item.likesCount)
            item.like()
        }
        notifyItemChanged(position)
    }

    override fun getData(): List<NewsItem> = currentList

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { onClick?.invoke(currentList[holder.bindingAdapterPosition]) }
        holder.itemView.share_btn.setOnClickListener { onShare?.invoke(currentList[holder.bindingAdapterPosition]) }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
        holder.itemView.share_btn.setOnClickListener(null)
    }
}