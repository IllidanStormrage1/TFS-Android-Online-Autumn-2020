package com.zkv.tfsfeed.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.zkv.tfsfeed.R
import kotlinx.android.synthetic.main.merge_item_header_profile.view.*

class HeaderProfileLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
) : ViewGroup(context, attributeSet, defStyleAttrs) {

    init {
        setWillNotDraw(true)
        inflate(context, R.layout.merge_item_header_profile, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = paddingTop + marginTop

        measureChildWithMargins(
            profile_avatar_iv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_avatar_iv.measuredHeight + profile_avatar_iv.marginTop

        measureChildWithMargins(
            profile_online_badge,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height = paddingTop + marginTop
        width += profile_avatar_iv.measuredWidth

        measureChildWithMargins(profile_name_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += profile_name_tv.measuredHeight + profile_name_tv.marginTop

        measureChildWithMargins(
            profile_domain_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_domain_tv.measuredHeight

        measureChildWithMargins(
            profile_domain_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_domain_tv.measuredHeight

        measureChildWithMargins(
            profile_last_seen_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_last_seen_tv.measuredHeight
        width = 0

        measureChildWithMargins(
            profile_about_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_about_tv.measuredHeight + profile_about_tv.marginTop

        measureChildWithMargins(
            profile_followers_count_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_followers_count_tv.measuredHeight + profile_followers_count_tv.marginTop

        measureChildWithMargins(profile_bday_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += profile_bday_tv.measuredHeight + profile_bday_tv.marginTop

        measureChildWithMargins(
            profile_home_town_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_home_town_tv.measuredHeight + profile_home_town_tv.marginTop

        measureChildWithMargins(profile_city_tv, widthMeasureSpec, width, heightMeasureSpec, height)
        height += profile_city_tv.measuredHeight + profile_city_tv.marginTop

        measureChildWithMargins(
            profile_career_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_career_tv.measuredHeight + profile_career_tv.marginTop

        measureChildWithMargins(
            profile_education_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_education_tv.measuredHeight + profile_education_tv.marginTop

        measureChildWithMargins(
            profile_relation_tv,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_relation_tv.measuredHeight + profile_relation_tv.marginTop

        measureChildWithMargins(
            profile_create_post,
            widthMeasureSpec,
            width,
            heightMeasureSpec,
            height
        )
        height += profile_create_post.measuredHeight + profile_create_post.marginTop

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentStart = paddingStart + marginLeft
        var currentTop = paddingTop + marginTop

        profile_avatar_iv.layout(
            currentStart + profile_avatar_iv.marginStart,
            currentTop + profile_avatar_iv.marginTop,
            currentStart + profile_avatar_iv.measuredWidth + profile_avatar_iv.marginStart,
            currentTop + profile_avatar_iv.measuredHeight + profile_avatar_iv.marginTop
        )
        currentStart += profile_avatar_iv.measuredWidth + profile_avatar_iv.marginStart
        currentTop += profile_avatar_iv.measuredHeight

        profile_online_badge.layout(
            currentStart - profile_avatar_iv.marginStart,
            currentTop,
            currentStart + profile_online_badge.measuredWidth - profile_avatar_iv.marginStart,
            currentTop + profile_online_badge.measuredHeight,
        )
        currentTop = paddingTop + marginTop

        profile_name_tv.layout(
            currentStart + profile_name_tv.marginStart,
            currentTop + profile_name_tv.marginTop,
            currentStart + profile_name_tv.measuredWidth + profile_name_tv.marginStart,
            currentTop + profile_name_tv.measuredHeight + profile_name_tv.marginTop
        )
        currentTop += profile_name_tv.measuredHeight + profile_name_tv.marginTop

        profile_domain_tv.layout(
            currentStart + profile_domain_tv.marginStart,
            currentTop,
            currentStart + profile_domain_tv.measuredWidth + profile_domain_tv.marginStart,
            currentTop + profile_domain_tv.measuredHeight
        )
        currentTop += profile_domain_tv.measuredHeight

        profile_last_seen_tv.layout(
            currentStart + profile_last_seen_tv.marginStart,
            currentTop,
            currentStart + profile_last_seen_tv.measuredWidth + profile_last_seen_tv.marginStart,
            currentTop + profile_last_seen_tv.measuredHeight
        )
        currentTop += profile_last_seen_tv.measuredHeight

        currentTop = (profile_avatar_iv.measuredHeight + profile_avatar_iv.marginTop).coerceAtLeast(
            profile_name_tv.measuredHeight + profile_name_tv.marginTop + profile_domain_tv.measuredHeight + profile_last_seen_tv.measuredHeight
        ) + paddingTop + marginTop
        currentStart = 0

        profile_about_tv.layout(
            currentStart + profile_about_tv.marginStart,
            currentTop + profile_about_tv.marginTop,
            currentStart + profile_about_tv.measuredWidth + profile_about_tv.marginStart,
            currentTop + profile_about_tv.measuredHeight + profile_about_tv.marginTop
        )
        currentTop += profile_about_tv.measuredHeight + profile_about_tv.marginTop

        profile_followers_count_tv.layout(
            currentStart + profile_followers_count_tv.marginStart,
            currentTop + profile_followers_count_tv.marginTop,
            currentStart + profile_followers_count_tv.measuredWidth + profile_followers_count_tv.marginStart,
            currentTop + profile_followers_count_tv.measuredHeight + profile_followers_count_tv.marginTop
        )
        currentTop += profile_followers_count_tv.measuredHeight + profile_followers_count_tv.marginTop

        profile_bday_tv.layout(
            currentStart + profile_bday_tv.marginStart,
            currentTop + profile_bday_tv.marginTop,
            currentStart + profile_bday_tv.measuredWidth + profile_bday_tv.marginStart,
            currentTop + profile_bday_tv.measuredHeight + profile_bday_tv.marginTop
        )
        currentTop += profile_bday_tv.measuredHeight + profile_bday_tv.marginTop

        profile_home_town_tv.layout(
            currentStart + profile_home_town_tv.marginStart,
            currentTop + profile_home_town_tv.marginTop,
            currentStart + profile_home_town_tv.measuredWidth + profile_home_town_tv.marginStart,
            currentTop + profile_home_town_tv.measuredHeight + profile_home_town_tv.marginTop
        )
        currentTop += profile_home_town_tv.measuredHeight + profile_home_town_tv.marginTop

        profile_city_tv.layout(
            currentStart + profile_city_tv.marginStart,
            currentTop + profile_city_tv.marginTop,
            currentStart + profile_city_tv.measuredWidth + profile_city_tv.marginStart,
            currentTop + profile_city_tv.measuredHeight + profile_city_tv.marginTop
        )
        currentTop += profile_city_tv.measuredHeight + profile_city_tv.marginTop

        profile_career_tv.layout(
            currentStart + profile_career_tv.marginStart,
            currentTop + profile_career_tv.marginTop,
            currentStart + profile_career_tv.measuredWidth + profile_career_tv.marginStart,
            currentTop + profile_career_tv.measuredHeight + profile_career_tv.marginTop
        )
        currentTop += profile_career_tv.measuredHeight + profile_career_tv.marginTop

        profile_education_tv.layout(
            currentStart + profile_education_tv.marginStart,
            currentTop + profile_education_tv.marginTop,
            currentStart + profile_education_tv.measuredWidth + profile_education_tv.marginStart,
            currentTop + profile_education_tv.measuredHeight + profile_education_tv.marginTop
        )
        currentTop += profile_education_tv.measuredHeight + profile_education_tv.marginTop

        profile_relation_tv.layout(
            currentStart + profile_relation_tv.marginStart,
            currentTop + profile_relation_tv.marginTop,
            currentStart + profile_relation_tv.measuredWidth + profile_relation_tv.marginStart,
            currentTop + profile_relation_tv.measuredHeight + profile_relation_tv.marginTop
        )
        currentTop += profile_relation_tv.measuredHeight + profile_relation_tv.marginTop

        profile_create_post.layout(
            currentStart + profile_create_post.marginStart,
            currentTop + profile_create_post.marginTop,
            currentStart + profile_create_post.measuredWidth + profile_create_post.marginStart,
            currentTop + profile_create_post.measuredHeight + profile_create_post.marginTop
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams =
        MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
}
