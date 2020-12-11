package com.zkv.tfsfeed.domain.utils

import android.content.res.Resources
import android.text.format.DateUtils
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import kotlin.math.ln
import kotlin.math.pow

private const val DATE_FORMAT = "d MMMM H:mm"
private const val COUNT_SUFFIX = "%.1f%c"
private const val SUFFIX_STRING = "KMGTPE"

fun dateStringFromTimeInMillis(timeInMillis: Long): String =
    SimpleDateFormat(
        DATE_FORMAT,
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
    ).format(
        timeInMillis
    )

fun prepareDateString(timeInMilliseconds: Long): String =
    DateUtils.getRelativeTimeSpanString(
        timeInMilliseconds,
        System.currentTimeMillis(),
        DateUtils.DAY_IN_MILLIS
    ).toString()

fun getTimeSpan(timeInMilliseconds: Long) =
    DateUtils.getRelativeTimeSpanString(timeInMilliseconds).toString()

fun <T> MutableCollection<T>.clearAndAddAll(collection: Collection<T>) {
    clear()
    addAll(collection)
}

val Int.currencyCountWithSuffix: String
    get() {
        if (this < 1000) return this.toString()
        val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
        return String.format(
            COUNT_SUFFIX,
            this / 1000.0.pow(exp.toDouble()),
            SUFFIX_STRING[exp - 1]
        )
    }
