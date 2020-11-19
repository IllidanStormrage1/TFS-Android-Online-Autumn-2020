package com.zkv.tfsfeed.presentation.extensions

import android.view.View

class ThrottleClickListener(private val time: Long, private val onClick: (View?) -> Unit) :
    View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time) {
            onClick.invoke(v)
            lastClickTime = currentTimeMillis
        }
    }
}