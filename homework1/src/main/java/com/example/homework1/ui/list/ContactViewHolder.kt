package com.example.homework1.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.homework1.domain.ContactProfile
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rc_contact_item.view.*

class ContactViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(item: ContactProfile) {
        with(containerView) {
            item_name_tv.text = item.name
            item_phone_tv.text = item.phoneNumbers.joinToString("\n")
        }
    }
}