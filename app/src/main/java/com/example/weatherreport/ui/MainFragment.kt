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

        viewModel.showWeather().observe(viewLifecycleOwner) {
            binding.tvCityName.text = it.location.name
            binding.tvTemperature.text = it.current.temp_c.toString()
            binding.tvSummary.text = it.current.condition.text
            binding.tvLastUpdateTime.text = it.current.last_updated

            hourAdapter.submitList(it.forecast.forecastday[0].hour)
            weekDayAdapter.submitList(it.forecast.forecastday.drop(1))
        }
    }
}