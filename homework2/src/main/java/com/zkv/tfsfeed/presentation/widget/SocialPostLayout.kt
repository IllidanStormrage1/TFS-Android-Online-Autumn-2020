package com.zkv.tfsfeed.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.*
import com.zkv.tfsfeed.R
import kotlinx.android.synthetic.main.merge_item_post.view.*

class SocialPostLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    var isImage = false
    var isAvailableToDownload = false

    init {
        setWillNotDraw(true)
        clipToOutline = true
        inflate(context, R.layout.merge_item_post, this)
        context.withStyledAttributes(attributeSet, R.styleable.SocialPostLayout, defStyleAttrs) {
            isImage = getBoolean(R.styleable.SocialPostLayout_isImage, false)
            content_tv.maxLines = getInteger(R.styleable.SocialPostLayout_textMaxLines, 15)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        var width = 0

        measureChildWithMargins(avatar_iv, widthMeasureSpec, width, heightMeasureSpec, height)
        width += avatar_iv.measuredWidth

        measureChildWithMargins(group_name_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        if (isImage && content_iv.isVisible && isAvailableToDownload)
            measureChildWithMargins(download_iv, widthMeasureSpec, width, heightMeasureSpec, height)

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

        if (content_tv.text.isNotBlank()) {
            measureChildWithMargins(content_tv, widthMeasureSpec, width, heightMeasureSpec, height)
            height += content_tv.measuredHeight + content_tv.marginTop
        }

        if (isImage && content_iv.isVisible) {
            measureChildWithMargins(content_iv, widthMeasureSpec, width, heightMeasureSpec, height)
            height += content_iv.measuredHeight + content_iv.marginTop
        }

        measureChildWithMargins(like_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += like_btn.measuredWidth + like_btn.marginStart

        measureChildWithMargins(comment_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += comment_btn.measuredWidth

        measureChildWithMargins(share_btn, widthMeasureSpec, width, heightMeasureSpec, height)
        width += share_btn.measuredWidth

        measureChildWithMargins(views_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        measureChildWithMargins(views_iv, widthMeasureSpec, width, heightMeasureSpec, height)

        height += like_btn.measuredHeight + like_btn.marginTop
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentStart = paddingStart + marginLeft
        var currentTop = paddingTop + marginTop

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

        if (content_tv.text.isNotBlank()) {
            content_tv.layout(
                currentStart + content_tv.marginStart,
                currentTop + content_tv.marginTop,
                measuredWidth - content_tv.marginEnd,
                currentTop + content_tv.measuredHeight + content_tv.marginTop
            )
            currentTop += content_tv.measuredHeight + content_tv.marginTop
        }

        if (isImage) {
            content_iv.layout(
                currentStart + content_iv.marginStart,
                currentTop + content_iv.marginTop,
                measuredWidth - content_iv.marginEnd,
                currentTop + content_iv.measuredHeight + content_iv.marginTop
            )
            currentTop += content_iv.measuredHeight + content_iv.marginTop
        }

        like_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop + like_btn.marginTop,
            like_btn.measuredWidth + like_btn.marginStart + currentStart,
            currentTop + like_btn.measuredHeight
        )
        currentStart += like_btn.measuredWidth
        currentTop += like_btn.marginTop

        comment_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop,
            comment_btn.measuredWidth + like_btn.marginStart + currentStart,
            currentTop + comment_btn.measuredHeight
        )
        currentStart += comment_btn.measuredWidth

        share_btn.layout(
            currentStart + like_btn.marginStart,
            currentTop,
            share_btn.measuredWidth + like_btn.marginStart + currentStart,
            currentTop + share_btn.measuredHeight
        )
        currentStart += share_btn.measuredWidth

        if (isImage && isAvailableToDownload)
            download_iv.layout(
                currentStart + like_btn.marginStart,
                currentTop,
                download_iv.measuredWidth + like_btn.marginStart + currentStart,
                currentTop + share_btn.measuredHeight
            )

        val partHeight = views_tv.measuredHeight / 2
        val baseLine = currentTop + share_btn.measuredHeight / 2
        val topViews = baseLine - partHeight
        val bottomViews = baseLine + partHeight
        views_tv.layout(
            measuredWidth - views_tv.measuredWidth - views_tv.marginEnd,
            topViews,
            measuredWidth - views_tv.marginEnd,
            bottomViews
        )

        views_iv.layout(
            views_tv.left - views_iv.marginEnd - views_iv.measuredWidth,
            topViews,
            views_tv.left - views_iv.marginEnd,
            bottomViews
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
}