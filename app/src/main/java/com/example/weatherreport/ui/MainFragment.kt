package com.example.weatherreport.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<WeatherViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val hourAdapter = HourStatsAdapter()
        binding.rvStatsPerHour.adapter = hourAdapter

        val weekDayAdapter = WeekWeatherAdapter()
        binding.rvWeekWeather.adapter = weekDayAdapter

        viewModel.showWeather().observe(viewLifecycleOwner) { resource ->
            if (resource.data != null && resource.data.isNotEmpty()) {
                binding.tvCityName.text = resource.data[0].location.name
                binding.tvTemperature.text = resource.data[0].current.temp_c.toString()
                binding.tvSummary.text = resource.data[0].current.condition.text
                binding.tvLastUpdateTime.text = resource.data[0].current.last_updated

                hourAdapter.submitList(resource.data[0].forecast.forecastday[0].hour)
                weekDayAdapter.submitList(resource.data[0].forecast.forecastday)
            }
        }
    }
}