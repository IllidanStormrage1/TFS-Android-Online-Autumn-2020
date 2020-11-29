package com.zkv.tfsfeed.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.zkv.tfsfeed.R
import kotlinx.android.synthetic.main.merge_comment.view.*

class CommentLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        setWillNotDraw(true)
        inflate(context, R.layout.merge_comment, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = 0

        measureChildWithMargins(comment_avatar_iv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height)
        width += comment_avatar_iv.measuredWidth
        height += comment_avatar_iv.marginTop

        measureChildWithMargins(comment_name_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += comment_name_tv.measuredHeight


        if (comment_content_iv.isVisible) {
            measureChildWithMargins(comment_content_iv,
                widthMeasureSpec,
                width,
                heightMeasureSpec,
                height)
            height += comment_content_iv.measuredHeight + comment_content_iv.marginTop
        }

        if (comment_text_tv.isVisible) {
            measureChildWithMargins(comment_text_tv,
                widthMeasureSpec,
                width,
                heightMeasureSpec,
                height)
            height += comment_text_tv.measuredHeight
        }

        measureChildWithMargins(comment_date,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height)

        measureChildWithMargins(comment_likes_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height)
        height += comment_date.measuredHeight + comment_date.marginTop + comment_date.marginTop

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentStart = paddingStart + marginLeft
        var currentTop = paddingTop + marginTop

        comment_avatar_iv.layout(
            currentStart + comment_avatar_iv.marginStart,
            currentTop + comment_avatar_iv.marginTop,
            currentStart + comment_avatar_iv.measuredWidth + comment_avatar_iv.marginStart,
            currentTop + comment_avatar_iv.measuredHeight + comment_avatar_iv.marginTop
        )
        currentStart += comment_avatar_iv.measuredWidth + comment_avatar_iv.marginStart

        comment_name_tv.layout(
            currentStart + comment_name_tv.marginStart,
            currentTop + comment_name_tv.marginTop,
            currentStart + comment_name_tv.measuredWidth + comment_name_tv.marginStart,
            currentTop + comment_name_tv.measuredHeight + comment_name_tv.marginTop)
        currentTop += comment_name_tv.measuredHeight + comment_name_tv.marginTop

        if (comment_text_tv.isVisible) {
            comment_text_tv.layout(
                currentStart + comment_text_tv.marginStart,
                currentTop,
                currentStart + comment_text_tv.measuredWidth + comment_text_tv.marginStart,
                currentTop + comment_text_tv.measuredHeight
            )
            currentTop += comment_text_tv.measuredHeight
        }

        if (comment_content_iv.isVisible) {
            comment_content_iv.layout(
                currentStart + comment_content_iv.marginStart,
                currentTop + comment_content_iv.marginTop,
                currentStart + comment_content_iv.measuredWidth + comment_content_iv.marginStart,
                currentTop + comment_content_iv.measuredHeight + comment_content_iv.marginTop
            )
            currentTop += comment_content_iv.measuredHeight
        }

        comment_date.layout(
            currentStart + comment_date.marginStart,
            currentTop + comment_date.marginTop + comment_date.marginTop,
            currentStart + comment_date.measuredWidth + comment_date.marginStart,
            currentTop + comment_date.measuredHeight + comment_date.marginTop + comment_date.marginTop
        )
        currentTop += comment_date.marginTop + comment_date.marginTop

        comment_likes_tv.layout(
            width - comment_likes_tv.measuredWidth,
            currentTop,
            width,
            height
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
}