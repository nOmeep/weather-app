package com.example.weatherreport.data.api.items.typeconverters

import androidx.room.TypeConverter
import com.example.weatherreport.data.api.items.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherItemTypeConverter {
    @TypeConverter
    fun weatherItemToString(wi: WeatherItem): String =
        Gson().toJson(wi)

    @TypeConverter
    fun stringToWeatherItem(str: String): WeatherItem =
        Gson().fromJson(str, object : TypeToken<WeatherItem>() {}.type)
}