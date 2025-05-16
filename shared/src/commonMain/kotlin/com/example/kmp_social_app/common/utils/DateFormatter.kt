package com.example.kmp_social_app.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

object DateFormatter {

    fun parseDate(dateStaring: String): String {
        return try {
            val dateTime = LocalDateTime.parse(dateStaring)

            val day = dateTime.dayOfMonth.toString()
            val month = dateTime.monthNumber.toString()
            val year = dateTime.year.toString()
            val hour = dateTime.time.hour.toString()
            val minute = dateTime.time.minute.toString()

            val dayStr = if (day.length == 1) "0$day" else day
            val monthStr = if (month.length == 1) "0$month" else month
            val hourStr = if (hour.length == 1) "0$hour" else hour
            val minuteStr = if (minute.length == 1) "0$minute" else minute

            val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            if (dateTime.date == current.date) {
                "Today $hourStr:$minuteStr"
            } else if (current.date.minus(dateTime.date) == DatePeriod(days = 1)) {
                "Yesterday $hourStr:$minuteStr"
            } else {
                "$dayStr-$monthStr-$year $hourStr:$minuteStr"
            }
        } catch (ex: Exception) {
            dateStaring
        }
    }
}