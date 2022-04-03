package com.example.weatherreport.util.permissions

import androidx.lifecycle.LifecycleObserver

abstract class AbstractPermissionObserver : LifecycleObserver {
    var granted = false
    var permissionsListener: PermissionsListener? = null

    abstract fun launch()

    abstract fun checkPermission(): Boolean
}