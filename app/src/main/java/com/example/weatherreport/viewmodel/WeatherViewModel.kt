package com.example.weatherreport.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.BuildConfig
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private var weatherStats = MutableLiveData<WeatherItem>()

    init {
        viewModelScope.launch {
            weatherStats.value = repository.getWeather("Москва", BuildConfig.API_KEY)
        }
    }

    fun showWeather() = weatherStats
}