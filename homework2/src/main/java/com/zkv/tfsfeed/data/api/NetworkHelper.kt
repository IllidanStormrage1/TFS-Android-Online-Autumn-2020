package com.zkv.tfsfeed.data.api

import android.content.Context
import com.zkv.tfsfeed.presentation.utils.extensions.isConnected
import javax.inject.Inject

class NetworkHelper @Inject constructor(context: Context) {

    val isConnected = context.isConnected()
}
