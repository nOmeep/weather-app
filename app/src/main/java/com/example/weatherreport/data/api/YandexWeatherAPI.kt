package com.example.weatherreport.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YandexWeatherAPI {
    companion object {
        const val BASE_URL = "https://api.weather.yandex.ru/v2/"
    }

    @GET("/forecast")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Header("X-Yandex-API-Key") key: String,
        @Query("lang") lang: String = "ru_RU"
    ): String
}