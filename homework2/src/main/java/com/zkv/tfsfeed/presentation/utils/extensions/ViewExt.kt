package com.zkv.tfsfeed.presentation.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.zkv.tfsfeed.presentation.utils.DebounceClickListener

fun ImageView.loadFromUrl(
    url: String?,
    @DrawableRes placeholderId: Int,
    options: RequestOptions = RequestOptions(),
) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(placeholderId)
        .error(placeholderId)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(options)
        .into(this)
}

fun ViewGroup.inflate(@LayoutRes resourceId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resourceId, this, attachToRoot)

fun TextView.setText(@StringRes resourceId: Int, vararg values: String) {
    text = resources.getString(resourceId, *values)
}

fun TextView.setText(@StringRes resourceId: Int, @StringRes vararg values: Int) {
    val stringValues = values.asList().map { resources.getString(it) }.toTypedArray()
    setText(resourceId, *stringValues)
}

fun TextView.setPluralText(@PluralsRes resourceId: Int, value: Int) {
    text = resources.getQuantityString(resourceId, value, value)
}

fun View.setOnDebounceClickListener(time: Long = 1000L, onClick: (View?) -> Unit) {
    setOnClickListener(DebounceClickListener(time, onClick))
}

var ShimmerFrameLayout.isLaunched: Boolean
    get() = isShimmerStarted && isVisible
    set(value) {
        isVisible = if (value) {
            startShimmer()
            true
        } else {
            stopShimmer()
            false
        }
    }

fun AppCompatTextView.setPrecomputedText(text: String) {
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this),
            null
        )
    )
}
