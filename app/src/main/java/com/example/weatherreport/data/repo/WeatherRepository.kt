package com.example.weatherreport.data.repo

import androidx.room.withTransaction
import com.example.weatherreport.data.api.WeatherAPI
import com.example.weatherreport.data.db.WeatherDatabase
import com.example.weatherreport.util.networkBoundResource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherAPI,
    private val db: WeatherDatabase
) {
    private val weatherDAO = db.weatherItemsDAO()

    fun getCurrentCityWeather(cityName: String, key: String) = networkBoundResource(
        query = {
            weatherDAO.getCityByName(cityName)
        },
        fetch = {
            api.getWeather(key, cityName)
        },
        saveFetchResult = { weatherItem ->
            db.withTransaction {
                weatherDAO.deleteCityByName(weatherItem.location.name)
                weatherDAO.cacheWeatherItem(weatherItem)
            }
        }
    )

    fun getAllCachedItems() = weatherDAO.getAllCachedItems()
}
