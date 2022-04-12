package com.example.weatherreport.data.repo

import androidx.room.withTransaction
import com.example.weatherreport.BuildConfig
import com.example.weatherreport.data.api.WeatherAPI
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.db.WeatherDatabase
import com.example.weatherreport.data.db.caching.networkBoundResource
import com.example.weatherreport.util.classes.Wrapper
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherAPI,
    private val db: WeatherDatabase,
) : WeatherRepositoryInterface {
    private val weatherDAO = db.weatherItemsDAO()

    override fun getWeather(name: Wrapper<String>) = networkBoundResource(
        query = {
            getCachedItemByName(name.value)
        },
        fetch = {
            fetchItemFromNetworkByName(name.value)
        },
        saveFetchResult = { weatherItem ->
            db.withTransaction {
                deleteItemByName(name.value)
                name.changeValue(weatherItem.location.name)
                deleteItemByName(name.value)
                insertItemInDatabase(weatherItem)
            }
        }
    )

    override fun getCachedItemByName(name: String) = weatherDAO.getCityByName(name)

    override fun getAllCachedItems() = weatherDAO.getAllCachedItems()

    override suspend fun fetchItemFromNetworkByName(name: String) =
        api.getWeather(BuildConfig.API_KEY, name)

    override suspend fun insertItemInDatabase(item: WeatherItem) = weatherDAO.cacheWeatherItem(item)

    override suspend fun deleteItemByName(name: String) = weatherDAO.deleteCityByName(name)
}
