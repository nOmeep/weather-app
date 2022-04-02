package com.example.weatherreport.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherreport.R
import com.example.weatherreport.util.permissions.LocationPermissionObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var locationPermissionObserver: LocationPermissionObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(locationPermissionObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(locationPermissionObserver)
    }
}