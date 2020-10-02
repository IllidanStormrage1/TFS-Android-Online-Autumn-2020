package com.example.homework2.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.children
import androidx.core.view.marginLeft
import androidx.core.view.marginTop

@Suppress("unused")
class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var measuredHeight = 0
        var lineWidth = 0
        var lineHeight = 0

        children.forEach { child ->
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight)

            if (lineWidth + child.measuredWidth > desiredWidth) {
                measuredHeight += lineHeight
                lineHeight = 0
                lineWidth = 0
            }
            lineHeight = maxOf(lineHeight, child.measuredHeight)
            lineWidth += child.measuredWidth
        }

        if (lineHeight != 0) measuredHeight += lineHeight

        setMeasuredDimension(desiredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = l + paddingLeft + marginLeft
        var currentTop = t + paddingLeft + marginTop
        var lineHeight = 0

        children.forEach { child ->
            if (currentLeft + child.measuredWidth > measuredWidth) {
                currentTop += lineHeight
                currentLeft = 0
                lineHeight = 0
            }
            child.layout(
                currentLeft,
                currentTop,
                currentLeft + child.measuredWidth,
                currentTop + child.measuredHeight
            )
            lineHeight = maxOf(lineHeight, child.measuredHeight)
            currentLeft += child.measuredWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet) = MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams() = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}