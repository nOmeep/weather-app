package com.example.weatherreport.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.weatherreport.util.constants.Constants
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.util.classes.Resource.Error
import com.example.weatherreport.util.classes.Resource.Loading
import com.example.weatherreport.util.funs.coordinatesToQuery
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

    private lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        binding.rvStatsPerHour.adapter = hourAdapter
        binding.rvWeekWeather.adapter = weekDayAdapter

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        val cityToShow =
            sharedPref.getString(Constants.SHARED_PREF_CITY_KEY, null) ?: Constants.DEFAULT_CITY

        viewModel.updateWeather(args.cityName, cityToShow)
            .observe(viewLifecycleOwner) { resource ->
                binding.pbLoading.isVisible = resource is Loading
                binding.tvError.isVisible = resource is Error && resource.data.isNullOrEmpty()

                val weatherItem = resource.data?.firstOrNull() ?: return@observe

                binding.tvCityName.text = weatherItem.location.name
                binding.tvTemperature.text = weatherItem.current.temp_c.toString()
                binding.tvSummary.text = weatherItem.current.condition.text
                binding.tvLastUpdateTime.text = weatherItem.current.last_updated

                hourAdapter.submitList(weatherItem.forecast.forecastday[0].hour)
                weekDayAdapter.submitList(weatherItem.forecast.forecastday)

                binding.tvTextTimeStats.isVisible = true
                binding.tvTextWeekWeather.isVisible = true

                with(sharedPref.edit()) {
                    putString(Constants.SHARED_PREF_CITY_KEY, weatherItem.location.name)
                    apply()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miSearchForCity -> {
                findNavController().navigate(MainFragmentDirections.fromMainFragmentToSearchFragment())
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}