package com.zkv.tfsfeed.presentation.ui.profile.header.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.presentation.utils.extensions.loadFromUrl
import com.zkv.tfsfeed.presentation.utils.extensions.setPluralText
import com.zkv.tfsfeed.presentation.utils.extensions.setText
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.merge_item_header_profile.*

class HeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(item: Profile) {
        item.run {
            profile_avatar_iv.loadFromUrl(avatarUrl, R.drawable.bg_circle_placeholder)
            if (about.isNotBlank())
                profile_about_tv.setText(R.string.label_about, about)
            else
                profile_about_tv.setText(R.string.label_about, R.string.label_not_indicated)
            if (bdate.isNotBlank())
                profile_bday_tv.setText(R.string.label_birthday, bdate)
            else
                profile_bday_tv.setText(R.string.label_birthday, R.string.label_not_indicated)
            if (career.isNotBlank())
                profile_career_tv.setText(R.string.label_career, career)
            else
                profile_career_tv.setText(R.string.label_career, R.string.label_not_indicated)
            profile_domain_tv.text = userId
            if (education.isNotBlank())
                profile_education_tv.setText(R.string.label_education, education)
            else
                profile_education_tv.setText(R.string.label_education, R.string.label_not_indicated)
            if (homeTown.isNotBlank())
                profile_home_town_tv.setText(R.string.label_home_town, homeTown)
            else
                profile_home_town_tv.setText(R.string.label_home_town, R.string.label_not_indicated)
            if (city.isNotBlank())
                profile_city_tv.setText(R.string.label_city, city)
            else
                profile_city_tv.setText(R.string.label_city, R.string.label_not_indicated)
            profile_name_tv.text = nickname
            profile_last_seen_tv.setText(R.string.label_last_seen, lastSeenStatus)
            profile_followers_count_tv.setPluralText(R.plurals.plural_subscribers, followers)
            if (online)
                profile_online_badge.setBackgroundResource(R.drawable.ic_online_status)
            else
                profile_online_badge.setBackgroundResource(R.drawable.ic_offline_status)
            profile_relation_tv.setText(R.string.label_relation,
                itemView.context.resources.getStringArray(R.array.relation_status)[relation])
        }
    }
}