package com.zkv.tfsfeed.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.zkv.tfsfeed.R
import kotlinx.android.synthetic.main.merge_input_comment.view.*

class CommentInputLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        setWillNotDraw(true)
        inflate(context, R.layout.merge_input_comment, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = paddingTop + marginTop + marginBottom + paddingBottom
        var width = 0

        measureChildWithMargins(
            detail_comment_et,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        width += detail_comment_et.measuredWidth + detail_comment_et.marginStart - detail_comment_et.marginEnd

        measureChildWithMargins(detail_send_iv, widthMeasureSpec, width, heightMeasureSpec, height)
        width += detail_send_iv.measuredWidth + detail_send_iv.marginStart - detail_send_iv.marginEnd
        height += detail_comment_et.measuredHeight + detail_comment_et.marginTop + detail_comment_et.marginBottom

        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val currentTop = 0
        var currentStart = 0

        detail_comment_et.layout(
            currentStart + detail_comment_et.marginStart,
            currentTop,
            measuredWidth - detail_send_iv.measuredWidth - detail_send_iv.marginEnd,
            currentTop + detail_comment_et.measuredHeight
        )
        currentStart += detail_comment_et.measuredWidth

        detail_send_iv.layout(
            measuredWidth - detail_send_iv.measuredWidth - detail_send_iv.marginEnd,
            currentTop,
            measuredWidth - detail_send_iv.marginEnd,
            currentTop + detail_send_iv.measuredHeight
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
}
