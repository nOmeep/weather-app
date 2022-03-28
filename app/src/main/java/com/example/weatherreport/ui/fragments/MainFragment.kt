package com.example.weatherreport.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.util.Resource.Loading
import com.example.weatherreport.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<WeatherViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val hourAdapter = HourStatsAdapter()
    private val weekDayAdapter = WeekWeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        binding.rvStatsPerHour.adapter = hourAdapter
        binding.rvWeekWeather.adapter = weekDayAdapter

        binding.ivChange.setOnClickListener {
            val action = MainFragmentDirections.fromMainFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getWeather("Москва").observe(viewLifecycleOwner) { resource ->
            binding.pbLoading.isVisible = resource is Loading
            val weatherItem = resource.data?.firstOrNull() ?: return@observe

            binding.tvCityName.text = weatherItem.location.name
            binding.tvTemperature.text = weatherItem.current.temp_c.toString()
            binding.tvSummary.text = weatherItem.current.condition.text
            binding.tvLastUpdateTime.text = weatherItem.current.last_updated

            hourAdapter.submitList(weatherItem.forecast.forecastday[0].hour)
            weekDayAdapter.submitList(weatherItem.forecast.forecastday)

            binding.tvTextTimeStats.isVisible = true
            binding.tvTextWeekWeather.isVisible = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}