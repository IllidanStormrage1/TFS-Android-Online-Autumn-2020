package com.zkv.tfsfeed.presentation.adapter.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.RecyclerView

class MainItemTouchHelper(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.SimpleCallback(0, START or END) {

    interface ItemTouchHelperAdapter {
        fun onItemSwipedStart(position: Int)
        fun onItemSwipedEnd(position: Int)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            START -> adapter.onItemSwipedStart(viewHolder.absoluteAdapterPosition)
            END -> adapter.onItemSwipedEnd(viewHolder.absoluteAdapterPosition)
        }
    }
}