package com.example.homework2.domain.utils

import android.text.format.DateUtils
import android.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

fun dateStringFromTimeInMillis(timeInMillis: Long): String =
    SimpleDateFormat("d MMMM h:mm", Locale.ROOT).format(timeInMillis)

fun prepareDateString(timeInMilliseconds: Long) = when {
    DateUtils.isToday(timeInMilliseconds) -> "Сегодня"
    else -> DateUtils.getRelativeTimeSpanString(timeInMilliseconds).toString()
}