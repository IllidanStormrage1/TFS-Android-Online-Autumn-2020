package com.example.homework2.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.example.homework2.R
import kotlinx.android.synthetic.main.item_post.view.*

@Suppress("unused")
class SocialPostLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_post, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        var width = 0

        measureChildWithMargins(avatar_iv, widthMeasureSpec, width, heightMeasureSpec, height)
        width += avatar_iv.measuredWidth

        measureChildWithMargins(group_name_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += group_name_tv.measuredHeight + group_name_tv.marginTop

        measureChildWithMargins(
            post_creation_date_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height = avatar_iv.measuredHeight.coerceAtLeast(
            group_name_tv.measuredHeight + group_name_tv.marginTop + post_creation_date_tv.measuredHeight
        )
        width = 0

        measureChildWithMargins(content_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += content_tv.measuredHeight + content_tv.marginTop

        measureChildWithMargins(content_iv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += content_iv.measuredHeight + content_iv.marginTop

        measureChildWithMargins(like_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += like_btn.measuredWidth + like_btn.marginStart
        height += like_btn.measuredHeight + like_btn.marginTop

        measureChildWithMargins(comment_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += comment_btn.measuredWidth

        measureChildWithMargins(share_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += share_btn.measuredWidth

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentStart = l + paddingStart + marginLeft
        var currentTop = l + paddingTop + marginTop

        avatar_iv.layout(
            currentStart + avatar_iv.marginStart,
            currentTop + avatar_iv.marginTop,
            avatar_iv.measuredWidth + avatar_iv.marginEnd,
            currentTop + avatar_iv.measuredHeight
        )
        currentStart += avatar_iv.measuredWidth

        group_name_tv.layout(
            currentStart + group_name_tv.marginStart,
            currentTop + group_name_tv.marginTop,
            measuredWidth - group_name_tv.marginEnd,
            currentTop + group_name_tv.measuredHeight + group_name_tv.marginTop
        )
        currentTop += group_name_tv.measuredHeight + group_name_tv.marginTop

        post_creation_date_tv.layout(
            currentStart + post_creation_date_tv.marginStart,
            currentTop + post_creation_date_tv.marginTop,
            currentStart + post_creation_date_tv.measuredWidth + post_creation_date_tv.marginStart,
            currentTop + post_creation_date_tv.measuredHeight
        )
        currentTop = avatar_iv.measuredHeight.coerceAtLeast(
            group_name_tv.measuredHeight + group_name_tv.marginTop + post_creation_date_tv.measuredHeight
        )
        currentStart = 0

        content_tv.layout(
            currentStart + content_tv.marginStart,
            currentTop + content_tv.marginTop,
            measuredWidth - content_tv.marginEnd,
            currentTop + content_tv.measuredHeight + content_tv.marginTop
        )
        currentTop += content_tv.measuredHeight + content_tv.marginTop

        content_iv.layout(
            currentStart + content_iv.marginStart,
            currentTop + content_iv.marginTop,
            measuredWidth - content_iv.marginEnd,
            currentTop + content_iv.measuredHeight + content_iv.marginTop
        )
        currentTop += content_iv.measuredHeight + content_iv.marginTop

        like_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop + like_btn.marginTop,
            like_btn.measuredWidth - like_btn.marginEnd,
            currentTop + like_btn.measuredHeight
        )
        currentStart += like_btn.measuredWidth

        comment_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop,
            currentStart + comment_btn.measuredWidth,
            currentTop + comment_btn.measuredHeight
        )
        currentStart += comment_btn.measuredWidth

        share_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop,
            currentStart + share_btn.measuredWidth,
            currentTop + share_btn.measuredHeight
        )
        currentStart += share_btn.measuredWidth
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}