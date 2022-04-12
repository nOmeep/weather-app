package com.example.weatherreport.di.modules

import android.app.Application
import androidx.room.Room
import com.example.weatherreport.data.db.WeatherDatabase
import com.example.weatherreport.data.db.WeatherDatabase.Companion.DATABASE_NAME
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
        Room.databaseBuilder(app, WeatherDatabase::class.java, DATABASE_NAME)
            .build()
}
