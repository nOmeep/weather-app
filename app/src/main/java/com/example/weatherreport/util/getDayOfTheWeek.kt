package com.example.weatherreport.util

import java.text.SimpleDateFormat
import java.util.Calendar

fun getDayOfTheWeek(date: String): String {
    val c = Calendar.getInstance()
    c.time = SimpleDateFormat("yyyy-mm-dd").parse(date)
    return when(c.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "ЧТ"
        Calendar.TUESDAY -> "ПТ"
        Calendar.WEDNESDAY -> "СБ"
        Calendar.THURSDAY -> "ВС"
        Calendar.FRIDAY -> "ПН"
        Calendar.SATURDAY -> "ВТ"
        Calendar.SUNDAY -> "СР"
        else -> "Error"
    }
}