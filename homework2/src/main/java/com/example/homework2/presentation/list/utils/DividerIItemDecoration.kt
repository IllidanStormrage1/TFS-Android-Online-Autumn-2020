package com.example.homework2.presentation.list.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.core.view.children
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView

interface DividerAdapterCallback {
    fun getData(position: Int): Int
}

class DividerIItemDecoration(
    @DimenRes private val verticalSpace: Int,
    @Dimension private val headerTextSize: Float,
    private val textColor: Int,
    private val callback: DividerAdapterCallback
) : RecyclerView.ItemDecoration() {

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = headerTextSize
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        parent.children.forEach { child ->
            val childPosition = parent.getChildAdapterPosition(child)
            val isHeader = isHeader(childPosition)
            if (childPosition == 0 || isHeader) {
                val fontMetrics = textPaint.fontMetrics
                val baseline = (child.top - verticalSpace + child.top - fontMetrics.bottom - fontMetrics.top) / 2
                canvas.drawText(
                    callback.getData(childPosition).toString(),
                    (child.width/2).toFloat(),
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
        state: RecyclerView.State
    ) {
        outRect.top = verticalSpace
    }

    private fun isHeader(position: Int): Boolean =
//            val preData = callback.getData(position - 1)
//            val data = callback.getData(position)
//            preData != data
            false

}