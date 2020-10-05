package com.example.homework2.presentation.list.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.domain.Post
import com.example.homework2.domain.utils.prepareDateString

class DividerItemDecoration(
    @DimenRes private val verticalSpace: Int,
    @Dimension private val headerTextSize: Float,
    @ColorInt private val textColor: Int,
    private val callback: DividerAdapterCallback,
) : RecyclerView.ItemDecoration() {

    interface DividerAdapterCallback {
        fun getData(): List<Post>
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isFakeBoldText = true
        color = textColor
        textSize = headerTextSize
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val items = callback.getData()
        val groupedItems = groupList(items)
        parent.children.forEach { child ->
            val childPosition = parent.getChildAdapterPosition(child)
            if (childPosition == RecyclerView.NO_POSITION) return@forEach
            val item = items[childPosition]
            if (childPosition == 0 || isHeader(groupedItems, item)) {
                val fontMetrics = textPaint.fontMetrics
                val baseline =
                    (child.top - verticalSpace + child.top - fontMetrics.bottom - fontMetrics.top) / 2
                canvas.drawText(
                    getKey(groupedItems, item),
                    (child.width / 2).toFloat(),
                    baseline,
                    textPaint
                )
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.top = verticalSpace
    }

    private fun isHeader(groupedItems: Map<String, List<Post>>, item: Post) =
        groupedItems.values.any { it[0] == item }

    private fun groupList(data: List<Post>) = data.groupBy { prepareDateString(it.dateInMills) }

    private fun getKey(groupedItems: Map<String, List<Post>>, item: Post) =
        groupedItems.keys.find { (groupedItems[it] ?: error("")).contains(item) } ?: ""
}