package com.example.weatherreport.di

import android.app.Application
import androidx.room.Room
import com.example.weatherreport.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application) =
        Room.databaseBuilder(app, WeatherDatabase::class.java, "weather_database")
            .build()
}