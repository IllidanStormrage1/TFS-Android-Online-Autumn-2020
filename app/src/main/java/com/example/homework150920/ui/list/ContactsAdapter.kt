package com.example.homework150920.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework150920.R
import com.example.homework150920.domain.ContactProfile

class ContactsAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    private val contacts = mutableListOf<ContactProfile>()

    fun submitList(newContacts: Collection<ContactProfile>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rc_contact_item, parent, false)
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}