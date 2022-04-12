package com.example.weatherreport.data.repo

import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.db.caching.Resource
import com.example.weatherreport.util.classes.Wrapper
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    fun getWeather(name: Wrapper<String>): Flow<Resource<List<WeatherItem>>>
    fun getCachedItemByName(name: String): Flow<List<WeatherItem>>
    fun getAllCachedItems(): Flow<List<WeatherItem>>
    suspend fun fetchItemFromNetworkByName(name: String): WeatherItem
    suspend fun deleteItemByName(name: String)
    suspend fun insertItemInDatabase(item: WeatherItem)
}