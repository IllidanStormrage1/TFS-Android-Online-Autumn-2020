package com.zkv.tfsfeed.presentation.adapter.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(@DimenRes private val spaceSize: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) return
            left = spaceSize / 2
            right = spaceSize / 2
            top = spaceSize
        }
    }
}