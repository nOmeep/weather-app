package com.example.weatherreport.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.weatherreport.ui.WeatherViewModel
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.util.classes.Resource.*
import com.example.weatherreport.util.constants.Constants
import com.example.weatherreport.util.permissions.LocationPermissionObserver
import com.example.weatherreport.util.permissions.PermissionsListener
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), PermissionsListener {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<WeatherViewModel>()
    private val args: MainFragmentArgs by navArgs()

    private val hourAdapter = HourStatsAdapter()
    private val weekDayAdapter = WeekWeatherAdapter()

    private lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var locationPermissionObserver: LocationPermissionObserver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        locationPermissionObserver.permissionsListener = this
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        if (locationPermissionObserver.checkPermission()) {
            LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation.addOnSuccessListener {
                sharedPref.edit().apply {
                    putFloat(Constants.SHARED_PREF_LATITUDE_KEY, it.latitude.toFloat())
                    putFloat(Constants.SHARED_PREF_LONGITUDE_KEY, it.longitude.toFloat())
                    apply()
                }
            }
        } else {
            locationPermissionObserver.launch()
        }

        binding.rvStatsPerHour.adapter = hourAdapter
        binding.rvWeekWeather.adapter = weekDayAdapter

        setHasOptionsMenu(true)
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

    override fun permissionGranted() {
        val cityToShow =
            sharedPref.getString(Constants.SHARED_PREF_CITY_KEY, null) ?: Constants.DEFAULT_CITY

        viewModel.updateWeather(args.cityName, cityToShow)
            .observe(viewLifecycleOwner) { resource ->
                val weatherItem = resource.data?.firstOrNull()

                when (resource) {
                    is Success -> {
                        binding.pbLoading.isVisible = false

                        binding.tvCityName.text = weatherItem!!.location.name
                        binding.tvTemperature.text = weatherItem.current.temp_c.toString()
                        binding.tvSummary.text = weatherItem.current.condition.text
                        binding.tvLastUpdateTime.text = weatherItem.current.last_updated

                        hourAdapter.submitList(weatherItem.forecast.forecastday[0].hour)
                        weekDayAdapter.submitList(weatherItem.forecast.forecastday)
                    }
                    is Loading -> {
                        binding.pbLoading.isVisible = true
                    }
                    is Error -> {
                        binding.tvError.isVisible = resource.data.isNullOrEmpty()
                        binding.pbLoading.isVisible = false
                    }
                }

                with(sharedPref.edit()) {
                    putString(Constants.SHARED_PREF_CITY_KEY, weatherItem?.location?.name)
                    apply()
                }
            }
    }

    override fun permissionDenied() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}