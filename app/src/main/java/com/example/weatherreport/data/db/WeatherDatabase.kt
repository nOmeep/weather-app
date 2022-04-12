package com.example.weatherreport.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.api.items.typeconverters.ForecastTypeConverter

@Database(entities = [WeatherItem::class], version = 1)
@TypeConverters(ForecastTypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherItemsDAO(): WeatherItemsDao
}
