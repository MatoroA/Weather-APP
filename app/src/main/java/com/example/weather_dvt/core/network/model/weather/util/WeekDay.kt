package com.example.weather_dvt.core.network.model.weather.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class DayOfWeek(val mill: Long, val displayName: WeekDay, val dayOfWeek: Int)


enum class WeekDay {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
}


@RequiresApi(Build.VERSION_CODES.O)
fun Long.asDayOfWeek(): DayOfWeek {
    val date = Date(this * 1000L).toInstant()
    val zoneId = ZoneId.of("Africa/Johannesburg")
    val zDateTime: ZonedDateTime = ZonedDateTime.ofInstant(date, zoneId)
    val dayOfWeek = zDateTime.dayOfWeek.value

    return DayOfWeek(mill = this, displayName = dayOfWeek.asWeekDay(), dayOfWeek = dayOfWeek)
}

fun Int.asWeekDay(): WeekDay = when (this) {
    Calendar.MONDAY -> WeekDay.Monday
    Calendar.TUESDAY -> WeekDay.Tuesday
    Calendar.WEDNESDAY -> WeekDay.Wednesday
    Calendar.THURSDAY -> WeekDay.Thursday
    Calendar.FRIDAY -> WeekDay.Friday
    Calendar.SATURDAY -> WeekDay.Saturday
    else -> WeekDay.Sunday
}