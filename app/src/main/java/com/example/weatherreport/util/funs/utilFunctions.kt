package com.example.weatherreport.util.funs

import androidx.core.view.isVisible
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.data.db.caching.Resource
import com.example.weatherreport.util.sharedprefernces.MyPreferences
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

fun FragmentMainBinding.bind(
    resource: Resource<List<WeatherItem>>,
    hourAdapter: HourStatsAdapter,
    weekDayAdapter: WeekWeatherAdapter,
    myPreferences: MyPreferences
) {
    val weatherItem = resource.data?.firstOrNull()
    when (resource) {
        is Resource.Success -> {
            this.pbLoading.isVisible = false

            this.tvCityName.text = weatherItem!!.location.name
            this.tvTemperature.text = weatherItem.current.temp_c.toString()
            this.tvSummary.text = weatherItem.current.condition.text
            this.tvLastUpdateTime.text = weatherItem.current.last_updated

            hourAdapter.submitList(weatherItem.forecast.forecastday[0].hour)
            weekDayAdapter.submitList(weatherItem.forecast.forecastday)

            myPreferences.saveLastShownCity(weatherItem.location.name)
        }
        is Resource.Loading -> {
            this.pbLoading.isVisible = true
        }
        is Resource.Error -> {
            this.tvError.isVisible = resource.data.isNullOrEmpty()
            this.pbLoading.isVisible = false
        }
    }
}
