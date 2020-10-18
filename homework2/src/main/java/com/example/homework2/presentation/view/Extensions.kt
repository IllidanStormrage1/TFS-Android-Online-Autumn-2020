package com.example.homework2.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.ShimmerFrameLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

var ShimmerFrameLayout.isStarting: Boolean
    set(value) {
        if (value) {
            isVisible = true
            startShimmer()
        } else {
            isVisible = false
            stopShimmer()
        }
    }
    get() = isShimmerStarted