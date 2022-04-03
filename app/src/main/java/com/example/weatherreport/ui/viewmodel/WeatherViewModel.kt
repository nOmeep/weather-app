package com.example.weatherreport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weatherreport.BuildConfig
import com.example.weatherreport.data.repo.WeatherRepository
import com.example.weatherreport.util.classes.Wrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun updateWeather(query: String) = repository.getCurrentCityWeather(
        Wrapper(query),
        BuildConfig.API_KEY
    ).asLiveData()

    fun getAllCachedItems() =
        repository.getAllCachedItems().asLiveData()

    fun removeItemByName(name: String) {
        runBlocking {
            repository.deleteItemByName(name)
        }
    }
}
