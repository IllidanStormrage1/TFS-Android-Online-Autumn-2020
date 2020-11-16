package com.zkv.tfsfeed.data.api

import android.content.Context
import com.zkv.tfsfeed.R
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandler @Inject constructor(private val context: Context) {

    fun getErrorMessage(throwable: Throwable): String = when (throwable) {
        is UnknownHostException -> context.resources.getString(R.string.message_unknown_host_exception)
        else -> context.resources.getString(R.string.message_error_default)
    }
}