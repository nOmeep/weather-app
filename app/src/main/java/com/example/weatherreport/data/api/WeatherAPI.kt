package com.example.weatherreport.data.api

import com.example.weatherreport.data.api.items.WeatherItem
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("days") days: Int = 7,
        @Query("lang") language: String = "ru"
    ): WeatherItem
}