package com.example.weatherreport.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentMainBinding
import com.example.weatherreport.ui.viewmodel.WeatherViewModel
import com.example.weatherreport.ui.adapters.HourStatsAdapter
import com.example.weatherreport.ui.adapters.WeekWeatherAdapter
import com.example.weatherreport.util.constants.Constants
import com.example.weatherreport.util.funs.bind
import com.example.weatherreport.util.permissions.LocationPermissionObserver
import com.example.weatherreport.util.permissions.PermissionsListener
import com.example.weatherreport.util.sharedprefernces.MyPreferences
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

    @Inject
    lateinit var myPreferences: MyPreferences

    @Inject
    lateinit var locationPermissionObserver: LocationPermissionObserver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)

        locationPermissionObserver.permissionsListener = this

        if (!locationPermissionObserver.checkPermission()) {
            locationPermissionObserver.launch()
        }
        locationPermissionObserver.requestLocation()

        binding.rvStatsPerHour.adapter = hourAdapter
        binding.rvWeekWeather.adapter = weekDayAdapter

        setHasOptionsMenu(true)
    }

    override fun permissionGranted() {
        val lastCity = myPreferences.getLastShownCityOrNull()
        val location = myPreferences.getLocationOrNull()

        viewModel.updateWeather(args.cityName ?: location ?: lastCity ?: Constants.DEFAULT_CITY)
            .observe(viewLifecycleOwner) { resource ->
                binding.bind(resource, hourAdapter, weekDayAdapter, myPreferences)
            }
    }

    override fun permissionDenied() {
        val lastCity = myPreferences.getLastShownCityOrNull()

        viewModel.updateWeather(args.cityName ?: lastCity ?: Constants.DEFAULT_CITY)
            .observe(viewLifecycleOwner) { resource ->
                binding.bind(resource, hourAdapter, weekDayAdapter, myPreferences)
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