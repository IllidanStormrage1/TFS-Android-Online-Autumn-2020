package com.example.homework150920.ui.service

import android.app.IntentService
import android.content.Intent
import android.provider.ContactsContract
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.homework150920.domain.ContactProfile
import com.example.homework150920.ui.CUSTOM_INTENT_ACTION
import com.example.homework150920.ui.EXTRA_DATA_NAME

class ContactsIntentService : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        sendBroadcast(getListOfContact())
    }

    private fun getListOfContact(): ArrayList<ContactProfile> {
        val contacts = arrayListOf<ContactProfile>()
        val contactsCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        contactsCursor?.let {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val numbers = getListOfNumbers(id)
                contacts.add(ContactProfile(name, numbers))
            }
        }
        contactsCursor?.close()
        return contacts
    }

    private fun getListOfNumbers(id: String): ArrayList<String> {
        val numbers = arrayListOf<String>()
        val phonesCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $id",
            null,
            null
        )
        phonesCursor?.let {
            while (it.moveToNext()) {
                val number =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                numbers.add(number)
            }
        }
        phonesCursor?.close()
        return numbers
    }

    private fun sendBroadcast(data: ArrayList<ContactProfile>) {
        val intent = Intent(CUSTOM_INTENT_ACTION)
        intent.putExtra(EXTRA_DATA_NAME, data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}