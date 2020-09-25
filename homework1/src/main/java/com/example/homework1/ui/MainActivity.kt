package com.example.homework1.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework1.R
import com.example.homework1.domain.ContactProfile
import com.example.homework1.ui.list.ContactsAdapter
import com.example.homework1.ui.list.SpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

const val SIMPLE_REQUEST_CODE = 0
const val EXTRA_DATA_NAME = "data"

class MainActivity : AppCompatActivity() {

    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        main_one_action_btn.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java), SIMPLE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            SIMPLE_REQUEST_CODE -> {
                if (intent != null) showSomeInformation(intent.getParcelableArrayListExtra(EXTRA_DATA_NAME))
            }
        }
    }

    private fun initRecyclerView() {
        contactsAdapter = ContactsAdapter()
        main_contacts_rcv.adapter = contactsAdapter
        main_contacts_rcv.addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_in_rc)))
    }

    private fun showSomeInformation(data: ArrayList<ContactProfile>?) {
        if (data != null) contactsAdapter.submitList(data)
    }
}