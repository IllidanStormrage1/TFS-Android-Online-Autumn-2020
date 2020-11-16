package com.zkv.tfsfeed.presentation.ui.profile.header

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.utils.clearAndAddAll
import com.zkv.tfsfeed.presentation.extensions.inflate
import com.zkv.tfsfeed.presentation.ui.profile.header.holder.HeaderViewHolder

class HeaderAdapter : RecyclerView.Adapter<HeaderViewHolder>() {

    private var items = mutableListOf<Profile>()

    fun submit(newItem: Profile) {
        val currentItemsIsEmpty = items.isEmpty()
        items.clearAndAddAll(listOf(newItem))
        if (currentItemsIsEmpty)
            notifyItemInserted(0)
        else
            notifyItemChanged(0, Unit)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder =
        HeaderViewHolder(parent.inflate(R.layout.item_header_profile))

    override fun getItemCount(): Int = items.size
}