package com.example.homework1.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.homework1.R
import com.example.homework1.ui.receiver.SimpleLocalBroadcastReceiver
import com.example.homework1.ui.service.ContactsIntentService
import kotlinx.android.synthetic.main.activity_second.*

const val REQUEST_CODE_PERMISSION_READ_CONTACTS = 1
const val CUSTOM_INTENT_ACTION = "com.example.homework150920.intent"

class SecondActivity : AppCompatActivity() {

    private val myReceiver = SimpleLocalBroadcastReceiver {
        setResult(SIMPLE_REQUEST_CODE, it)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        second_start_service_btn.setOnClickListener {
            if (isPermissionGranted())
                startIntentService()
            else
                requestPermissions()
        }
    }

    override fun onStart() {
        super.onStart()
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(myReceiver, IntentFilter(CUSTOM_INTENT_ACTION))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startIntentService()
            }
        }
    }

    private fun isPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            REQUEST_CODE_PERMISSION_READ_CONTACTS
        )
    }

    private fun startIntentService() {
        second_start_service_btn.isEnabled = false
        val intent = Intent(this, ContactsIntentService::class.java)
        startService(intent)
    }
}