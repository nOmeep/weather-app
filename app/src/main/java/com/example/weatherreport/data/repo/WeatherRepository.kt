package com.example.weatherreport.data.repo

import com.example.weatherreport.data.api.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherAPI
) {
    suspend fun getWeather(query: String, key: String) =
        api.getWeather(key, query)
}