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

            val day = dateTime.dayOfMonth
            val month = dateTime.monthNumber
            val year = dateTime.year
            val hour = dateTime.time.hour
            val minute = dateTime.time.minute

            val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            if (dateTime.date == current.date) {
                "Today $hour:$minute"
            } else if (current.date.minus(dateTime.date) == DatePeriod(days = 1)) {
                "Yesterday $hour:$minute"
            } else {
                "$day-$month-$year $hour:$minute"
            }
        } catch (ex: Exception) {
            dateStaring
        }
    }
}