package com.example.weatherreport.data.api.items

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherreport.data.api.items.typeconverters.ForecastTypeConverter

@Entity(tableName = "last_cached_page")
data class WeatherItem(
    @Embedded
    val current: Current,
    @Embedded
    val forecast: Forecast,
    @Embedded
    @PrimaryKey(autoGenerate = false)
    val location: Location
) {
    data class Current(
        @Embedded
        val condition: Condition,
        val feelslike_c: Double,
        val last_updated: String,
        val temp_c: Double,
    )

    data class Forecast(
        @TypeConverters(ForecastTypeConverter::class)
        var forecastday: List<Forecastday>
    ) {
        data class Forecastday(
            val date: String,
            val day: Day,
            val hour: List<Hour>
        ) {

            data class Day(
                val avgtemp_c: Double,
                val condition: Condition,
                val maxtemp_c: Double,
                val mDoubleemp_c: Double,
            )

            data class Hour(
                val condition: Condition,
                val feelslike_c: Double,
                val temp_c: Double,
                val time: String,
            )
        }
    }

    data class Location(
        val country: String,
        val name: String,
    )

    data class Condition(
        val icon: String,
        val text: String
    )
}