package com.zkv.tfsfeed.domain.utils

import android.content.res.Resources
import android.text.format.DateUtils
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import kotlin.math.*

private const val DATE_FORMAT = "d MMMM H:mm"
private const val COUNT_SUFFIX = "%.1f%c"
private const val SUFFIX_STRING = "KMB"

fun dateStringFromTimeInMillis(timeInMillis: Long): String =
    SimpleDateFormat(
        DATE_FORMAT,
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
    ).format(timeInMillis)

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
        if (abs(this) < 1000) return toString()
        val exp = round(ln(toDouble()) / ln(1000.0))
        return String.format(
            COUNT_SUFFIX,
            this / 1000.0.pow(exp),
            SUFFIX_STRING[exp.toInt() - 1]
        )
    }
