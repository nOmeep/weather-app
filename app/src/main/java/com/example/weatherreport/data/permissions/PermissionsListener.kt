package com.example.weatherreport.data.permissions

interface PermissionsListener {
    fun permissionGranted()
    fun permissionDenied()
}