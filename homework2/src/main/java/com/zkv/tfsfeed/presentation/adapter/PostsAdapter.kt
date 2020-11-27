package com.zkv.tfsfeed.presentation.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.model.like
import com.zkv.tfsfeed.presentation.adapter.holder.SocialPostViewHolder
import com.zkv.tfsfeed.presentation.adapter.utils.DiffCallback
import com.zkv.tfsfeed.presentation.adapter.utils.DividerItemDecoration
import com.zkv.tfsfeed.presentation.adapter.utils.MainItemTouchHelper
import com.zkv.tfsfeed.presentation.utils.extensions.inflate
import kotlinx.android.synthetic.main.merge_item_post.view.*

private typealias ItemHandler = ((item: NewsItem) -> Unit)?
private typealias onLikeHandler = ((itemId: Int, sourceId: Int, type: String, canLike: Int, likesCount: Int) -> Unit)?

class PostsAdapter(
    private inline val onIgnoreHandler: ((itemId: Int, sourceId: Int) -> Unit)? = null,
    private inline val onLikeHandler: onLikeHandler = null,
    private inline val onClickHandler: ItemHandler = null,
    private inline val onShareHandler: ItemHandler = null,
) : RecyclerView.Adapter<SocialPostViewHolder>(),
    MainItemTouchHelper.ItemTouchHelperAdapter, DividerItemDecoration.DividerAdapterCallback {

    private val differ = AsyncListDiffer(this, DiffCallback())
    private val currentList: List<NewsItem> get() = differ.currentList

    fun submitList(newItems: List<NewsItem>) {
        differ.submitList(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialPostViewHolder =
        SocialPostViewHolder(parent.inflate(R.layout.item_rc_post))

    override fun onBindViewHolder(holder: SocialPostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: SocialPostViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (payloads.isEmpty())
            onBindViewHolder(holder, position)
        else
            holder.update(payloads[0] as Bundle)
    }

    override fun getItemCount(): Int = currentList.size

    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
        super.setStateRestorationPolicy(StateRestorationPolicy.PREVENT_WHEN_EMPTY)
    }

    override fun onItemSwipedStart(position: Int) {
        val item = currentList[position]
        onIgnoreHandler?.invoke(item.id, item.sourceId)
        currentList.toMutableList().also { items ->
            items.removeAt(position)
            submitList(items)
        }
    }

    override fun onItemSwipedEnd(position: Int) {
        currentList[position].also { item ->
            onLikeHandler?.invoke(item.id, item.sourceId, item.type, item.canLike, item.likesCount)
            item.like()
        }
        notifyItemChanged(position)
    }

    override fun getData(): List<NewsItem> = currentList

    override fun onViewAttachedToWindow(holder: SocialPostViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { onClickHandler?.invoke(currentList[holder.bindingAdapterPosition]) }
        holder.itemView.share_btn.setOnClickListener { onShareHandler?.invoke(currentList[holder.bindingAdapterPosition]) }
    }

    override fun onViewDetachedFromWindow(holder: SocialPostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
        holder.itemView.share_btn.setOnClickListener(null)
    }
}