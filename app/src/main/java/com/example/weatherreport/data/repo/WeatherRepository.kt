package com.example.weatherreport.data.repo

import androidx.room.withTransaction
import com.example.weatherreport.data.api.WeatherAPI
import com.example.weatherreport.data.db.WeatherDatabase
import com.example.weatherreport.util.classes.Wrapper
import com.example.weatherreport.util.funs.networkBoundResource
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
                weatherDAO.deleteCityByName(cityName.value)
                cityName.changeValue(weatherItem.location.name)
                weatherDAO.deleteCityByName(cityName.value)
                weatherDAO.cacheWeatherItem(weatherItem)
            }
        }
    )

    fun getAllCachedItems() = weatherDAO.getAllCachedItems()

    suspend fun deleteItemByName(cityName: String) = weatherDAO.deleteCityByName(cityName)
}
