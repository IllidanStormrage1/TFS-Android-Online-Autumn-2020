package com.example.homework2.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadFromUrl(url: String?, @DrawableRes placeholderId: Int) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(placeholderId)
        .error(placeholderId)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)
}

fun ViewGroup.inflate(@LayoutRes resourceId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resourceId, this, attachToRoot)