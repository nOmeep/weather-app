package com.example.weatherreport.data.repo

import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.db.caching.Resource
import com.example.weatherreport.util.classes.Wrapper
import kotlinx.coroutines.flow.Flow

class FakeRepository : WeatherRepositoryInterface {
    override fun getWeather(name: Wrapper<String>): Flow<Resource<List<WeatherItem>>> {
        TODO("Not yet implemented")
    }

    override fun getCachedItemByName(name: String): Flow<List<WeatherItem>> {
        TODO("Not yet implemented")
    }

    override fun getAllCachedItems(): Flow<List<WeatherItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchItemFromNetworkByName(name: String): WeatherItem {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemByName(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertItemInDatabase(item: WeatherItem) {
        TODO("Not yet implemented")
    }
}
