package com.zkv.tfsfeed.presentation.ui.detail.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.clearAndAddAll
import com.zkv.tfsfeed.presentation.extensions.inflate
import com.zkv.tfsfeed.presentation.ui.detail.list.holder.HeaderPostViewHolder
import kotlinx.android.synthetic.main.merge_item_post.view.*

class HeaderPostAdapter(private val shareClickHandler: (NewsItem) -> Unit) :
    RecyclerView.Adapter<HeaderPostViewHolder>() {

    private var items = mutableListOf<NewsItem>()

    fun submit(newItem: NewsItem) {
        val currentItemsIsEmpty = items.isEmpty()
        items.clearAndAddAll(listOf(newItem))
        if (currentItemsIsEmpty)
            notifyItemInserted(0)
        else
            notifyItemChanged(0, Unit)
    }

    override fun onBindViewHolder(holder: HeaderPostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderPostViewHolder =
        HeaderPostViewHolder(parent.inflate(R.layout.item_header_post))

    override fun getItemCount(): Int = items.size

    override fun onViewAttachedToWindow(holder: HeaderPostViewHolder) {
        holder.itemView.share_btn.setOnClickListener { shareClickHandler(items.first()) }
    }

    override fun onViewDetachedFromWindow(holder: HeaderPostViewHolder) {
        holder.itemView.share_btn.setOnClickListener(null)
    }
}