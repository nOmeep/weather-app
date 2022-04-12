package com.example.weatherreport.data.api.items.typeconverters

import androidx.room.TypeConverter
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastTypeConverter {
    @TypeConverter
    fun forecastToString(forecastList: List<Forecastday>): String =
        Gson().toJson(forecastList)

    @TypeConverter
    fun stringToForecast(str: String): List<Forecastday> =
        Gson().fromJson(str, object : TypeToken<List<Forecastday>>() {}.type)
}
