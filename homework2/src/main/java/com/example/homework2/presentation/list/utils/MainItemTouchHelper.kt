package com.example.homework2.presentation.list.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {
    fun onItemSwipedLeft(position: Int)
    fun onItemSwipedRight(position: Int)
}

class MainItemTouchHelper(private val adapter: ItemTouchHelperAdapter) : ItemTouchHelper.SimpleCallback(0, START or END) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            START -> adapter.onItemSwipedLeft(viewHolder.absoluteAdapterPosition)
            END -> adapter.onItemSwipedRight(viewHolder.absoluteAdapterPosition)
        }
    }
}