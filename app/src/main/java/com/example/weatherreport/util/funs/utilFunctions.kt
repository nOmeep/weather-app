package com.example.weatherreport.util.funs

import java.text.SimpleDateFormat
import java.util.Calendar

fun String.getDayOfTheWeek(): String {
    val c = Calendar.getInstance()
    c.time = SimpleDateFormat("yyyy-mm-dd").parse(this)
    return when (c.get(Calendar.DAY_OF_WEEK)) {
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

fun coordinatesToQuery(lat: Float, lon: Float): String? =
    if (!(lat == 0f && lon == 0f)) "$lat,$lon" else null
