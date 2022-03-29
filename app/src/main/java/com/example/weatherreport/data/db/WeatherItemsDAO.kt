package com.example.weatherreport.data.db

import androidx.room.*
import com.example.weatherreport.data.api.items.WeatherItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherItemsDAO {
    @Query("select * from last_cached_page")
    fun getAllCachedItems(): Flow<List<WeatherItem>>

    @Query("select * from last_cached_page where name = :name limit 1")
    fun getCityByName(name: String): Flow<List<WeatherItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheWeatherItem(item: WeatherItem)

    @Query("delete from last_cached_page where name = :name")
    suspend fun deleteCityByName(name: String)
}
