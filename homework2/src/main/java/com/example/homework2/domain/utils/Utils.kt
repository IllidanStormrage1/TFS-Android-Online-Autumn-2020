package com.example.homework2.domain.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "d MMMM h:mm"

fun dateStringFromTimeInMillis(timeInMillis: Long): String =
    SimpleDateFormat(DATE_FORMAT, Locale.ROOT).format(timeInMillis)

fun prepareDateString(timeInMilliseconds: Long) = when {
    DateUtils.isToday(timeInMilliseconds) -> "Сегодня"
    else -> DateUtils.getRelativeTimeSpanString(timeInMilliseconds).toString()
}

fun <T> MutableCollection<T>.clearAndAddAll(collection: Collection<T>) {
    clear()
    addAll(collection)
}