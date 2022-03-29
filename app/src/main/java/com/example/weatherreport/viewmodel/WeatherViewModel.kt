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
    fun updateWeather(query: String) =
        repository.getCurrentCityWeather(query, BuildConfig.API_KEY).asLiveData()

    fun getAllCachedItems() =
        repository.getAllCachedItems().asLiveData()
}