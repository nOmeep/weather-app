package com.example.weatherreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weatherreport.BuildConfig
import com.example.weatherreport.data.repo.WeatherRepository
import com.example.weatherreport.util.Wrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    fun updateWeather(query: String?, cityToShow : String) =
        repository.getCurrentCityWeather(Wrapper(query ?: cityToShow), BuildConfig.API_KEY)
            .asLiveData()

    fun getAllCachedItems() =
        repository.getAllCachedItems().asLiveData()

    fun removeItemByName(name: String) {
        runBlocking {
            repository.deleteItemByName(name)
        }
    }
}
