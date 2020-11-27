package com.zkv.tfsfeed.presentation.utils

import android.view.View

class DebounceClickListener(private val time: Long, private val onClick: (View?) -> Unit) :
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