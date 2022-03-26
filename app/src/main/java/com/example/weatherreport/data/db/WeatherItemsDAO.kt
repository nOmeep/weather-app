package com.example.weatherreport.data.db

import androidx.room.*
import com.example.weatherreport.data.api.items.WeatherItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherItemsDAO {
    @Query("select * from cached_weather_items")
    fun getCachedItems(): Flow<List<WeatherItem>>

    @Query("select * from cached_weather_items where name =:name limit 1")
    fun getCachedCityByName(name: String) : Flow<WeatherItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheWeatherItem(item: WeatherItem)

    @Query("delete from cached_weather_items where name = :name")
    suspend fun deleteWeatherItemByCityName(name: String)
}