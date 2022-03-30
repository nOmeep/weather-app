package com.example.weatherreport.data.repo

import androidx.room.withTransaction
import com.example.weatherreport.data.api.WeatherAPI
import com.example.weatherreport.data.db.WeatherDatabase
import com.example.weatherreport.util.Wrapper
import com.example.weatherreport.util.networkBoundResource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherAPI,
    private val db: WeatherDatabase
) {
    private val weatherDAO = db.weatherItemsDAO()

    fun getCurrentCityWeather(cityName: Wrapper<String>, key: String) = networkBoundResource(
        query = {
            weatherDAO.getCityByName(cityName.value)
        },
        fetch = {
            api.getWeather(key, cityName.value)
        },
        saveFetchResult = { weatherItem ->
            db.withTransaction {
                cityName.changeValue(weatherItem.location.name)
                weatherDAO.deleteCityByName(weatherItem.location.name)
                weatherDAO.cacheWeatherItem(weatherItem)
            }
        }
    )

    fun getAllCachedItems() = weatherDAO.getAllCachedItems()
}
