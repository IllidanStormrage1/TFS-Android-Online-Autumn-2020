package com.zkv.tfsfeed.presentation.adapter.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.utils.prepareDateString

class DividerItemDecoration(
    @DimenRes private val verticalSpace: Int,
    @Dimension private val headerTextSize: Float,
    @ColorInt private val textColor: Int,
    private val callback: DividerAdapterCallback,
) : RecyclerView.ItemDecoration() {

    interface DividerAdapterCallback {
        fun getData(): List<NewsItem>
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
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
                    parent.measuredWidth / 2f,
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
        with(outRect) {
            top = verticalSpace
            left = verticalSpace / 2
            right = verticalSpace / 2
        }
    }

    private fun isHeader(groupedItems: Map<String, List<NewsItem>>, item: NewsItem) =
        groupedItems.values.any { it[0] == item }

    private fun groupList(data: List<NewsItem>) = data.groupBy { prepareDateString(it.dateInMills) }

    private fun getKey(groupedItems: Map<String, List<NewsItem>>, item: NewsItem) =
        groupedItems.keys.find { (groupedItems[it] ?: error("")).contains(item) } ?: ""
}