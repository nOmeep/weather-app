package com.example.weatherreport.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.util.Resource.Loading
import com.example.weatherreport.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<WeatherViewModel>()

    private val args: MainFragmentArgs by navArgs()

    private val hourAdapter = HourStatsAdapter()
    private val weekDayAdapter = WeekWeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        binding.rvStatsPerHour.adapter = hourAdapter
        binding.rvWeekWeather.adapter = weekDayAdapter

        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateWeather(args.weatherItem ?: "Москва")
            .observe(viewLifecycleOwner) { resource ->
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miSearchForCity -> {
                val action = MainFragmentDirections.fromMainFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}