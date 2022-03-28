package com.example.weatherreport.data.api.items

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherreport.data.api.items.typeconverters.ForecastTypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "last_cached_page")
data class WeatherItem(
    @Embedded
    val current: Current,
    @Embedded
    val forecast: Forecast,
    @Embedded
    @PrimaryKey(autoGenerate = false)
    val location: Location
) : Parcelable {
    @Parcelize
    data class Current(
        val cloud: Double,
        @Embedded
        val condition: Condition,
        val feelslike_c: Double,
        val feelslike_f: Double,
        val gust_kph: Double,
        val gust_mph: Double,
        val humidity: Double,
        val is_day: Double,
        val last_updated: String,
        val last_updated_epoch: Double,
        val precip_in: Double,
        val precip_mm: Double,
        val pressure_in: Double,
        val pressure_mb: Double,
        val temp_c: Double,
        val temp_f: Double,
        val uv: Double,
        val vis_km: Double,
        val vis_miles: Double,
        val wind_degree: Double,
        val wind_dir: String,
        val wind_kph: Double,
        val wind_mph: Double
    ) : Parcelable {

        @Parcelize
        data class Condition(
            val code: Double,
            val icon: String,
            val text: String
        ) : Parcelable
    }

    @Parcelize
    data class Forecast(
        @TypeConverters(ForecastTypeConverter::class)
        var forecastday: List<Forecastday>
    ) : Parcelable {
        @Parcelize
        data class Forecastday(
            val astro: Astro,
            val date: String,
            val date_epoch: Double,
            val day: Day,
            val hour: List<Hour>
        ) : Parcelable {
            @Parcelize
            data class Astro(
                val moon_illumination: String,
                val moon_phase: String,
                val moonrise: String,
                val moonset: String,
                val sunrise: String,
                val sunset: String
            ) : Parcelable

            @Parcelize
            data class Day(
                val avghumidity: Double,
                val avgtemp_c: Double,
                val avgtemp_f: Double,
                val avgvis_km: Double,
                val avgvis_miles: Double,
                val condition: ConditionX,
                val daily_chance_of_rain: Double,
                val daily_chance_of_snow: Double,
                val daily_will_it_rain: Double,
                val daily_will_it_snow: Double,
                val maxtemp_c: Double,
                val maxtemp_f: Double,
                val maxwind_kph: Double,
                val maxwind_mph: Double,
                val mDoubleemp_c: Double,
                val mDoubleemp_f: Double,
                val totalprecip_in: Double,
                val totalprecip_mm: Double,
                val uv: Double
            ) : Parcelable {
                @Parcelize
                data class ConditionX(
                    val code: Double,
                    val icon: String,
                    val text: String
                ) : Parcelable
            }

            @Parcelize
            data class Hour(
                val chance_of_rain: Double,
                val chance_of_snow: Double,
                val cloud: Double,
                val condition: ConditionXX,
                val dewpoDouble_c: Double,
                val dewpoDouble_f: Double,
                val feelslike_c: Double,
                val feelslike_f: Double,
                val gust_kph: Double,
                val gust_mph: Double,
                val heatindex_c: Double,
                val heatindex_f: Double,
                val humidity: Double,
                val is_day: Double,
                val precip_in: Double,
                val precip_mm: Double,
                val pressure_in: Double,
                val pressure_mb: Double,
                val temp_c: Double,
                val temp_f: Double,
                val time: String,
                val time_epoch: Double,
                val uv: Double,
                val vis_km: Double,
                val vis_miles: Double,
                val will_it_rain: Double,
                val will_it_snow: Double,
                val wind_degree: Double,
                val wind_dir: String,
                val wind_kph: Double,
                val wind_mph: Double,
                val windchill_c: Double,
                val windchill_f: Double
            ) : Parcelable {
                @Parcelize
                data class ConditionXX(
                    val code: Double,
                    val icon: String,
                    val text: String
                ) : Parcelable
            }
        }
    }

    @Parcelize
    data class Location(
        val country: String,
        val lat: Double,
        val localtime: String,
        val localtime_epoch: Double,
        val lon: Double,
        val name: String,
        val region: String,
        val tz_id: String
    ) : Parcelable
}