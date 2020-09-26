package com.example.homework2.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.children
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlin.math.max

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var currentWidth = 0
        var currentHeight = 0

        children.forEach { child ->
            measureChildWithMargins(child, widthMeasureSpec, currentWidth, heightMeasureSpec, currentHeight)
            currentWidth += child.measuredWidth

            if (currentWidth > desiredWidth) {
                currentWidth = 0
                currentHeight += child.measuredHeight
            } else {
                currentHeight = max(currentHeight, child.measuredHeight)
            }
        }

        setMeasuredDimension(desiredWidth, resolveSize(currentHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = l + paddingLeft + marginLeft
        var currentTop = t + paddingTop + marginTop

        children.forEach { child ->
            val width = currentLeft + child.measuredWidth
            if (width > measuredWidth) {
                currentTop += child.measuredHeight
                currentLeft = paddingLeft + marginLeft + l
            }
            child.layout(currentLeft, currentTop, width, currentTop + child.measuredHeight)
            currentLeft += child.measuredWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}