package com.example.weatherreport.data.api.items.typeconverters

import androidx.room.TypeConverter
import com.example.weatherreport.data.api.items.WeatherItem.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationTypeConverter {
    @TypeConverter
    fun currentToLocation(location: Location): String =
        Gson().toJson(location)

    @TypeConverter
    fun stringToLocation(str: String): Location =
        Gson().fromJson(str, object : TypeToken<Location>() {}.type)
}