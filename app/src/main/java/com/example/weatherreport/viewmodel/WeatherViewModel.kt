package com.example.weatherreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weatherreport.BuildConfig
import com.example.weatherreport.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private var weatherStatsLiveData = repository.getCurrentCityWeather("Москва", BuildConfig.API_KEY).asLiveData()

    fun showWeather() = weatherStatsLiveData

    fun updateWeather(query: String) {
        weatherStatsLiveData = repository.getCurrentCityWeather(query, BuildConfig.API_KEY).asLiveData()
    }
}