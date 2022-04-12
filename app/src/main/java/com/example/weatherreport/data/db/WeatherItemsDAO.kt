package com.example.weatherreport.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherreport.data.api.items.WeatherItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherItemsDAO {
    @Query("select * from last_cached_page")
    fun getAllCachedItems(): Flow<List<WeatherItem>>

    @Query("select * from last_cached_page where LOWER(name) = LOWER(:name) limit 1")
    fun getCityByName(name: String): Flow<List<WeatherItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheWeatherItem(item: WeatherItem)

    @Query("delete from last_cached_page where name = :name")
    suspend fun deleteCityByName(name: String)
}
