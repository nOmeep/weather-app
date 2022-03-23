package com.example.weatherreport.di

import com.example.weatherreport.data.api.YandexWeatherAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): YandexWeatherAPI = Retrofit.Builder()
        .baseUrl(YandexWeatherAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(YandexWeatherAPI::class.java)
}