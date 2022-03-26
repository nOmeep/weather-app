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

    fun getCurrentCityWeather(query: String, key: String) = networkBoundResource(
        query = {
            weatherDAO.getCachedItems()
        },
        fetch = {
            api.getWeather(key, query)
        },
        saveFetchResult = { weatherItem ->
            db.withTransaction {
                weatherDAO.deleteWeatherItemByCityName(weatherItem.location.name)
                weatherDAO.cacheWeatherItem(weatherItem)
            }
        }
    )
}