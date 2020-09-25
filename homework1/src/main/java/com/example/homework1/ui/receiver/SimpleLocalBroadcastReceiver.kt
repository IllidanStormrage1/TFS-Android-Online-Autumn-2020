package com.example.homework1.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.homework1.ui.EXTRA_DATA_NAME

class SimpleLocalBroadcastReceiver(private val action: (Intent) -> Unit) : BroadcastReceiver() {
    override fun onReceive(content: Context?, intent: Intent) {
        if (intent.hasExtra(EXTRA_DATA_NAME))
            action.invoke(intent)
    }
}