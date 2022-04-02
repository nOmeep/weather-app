package com.example.weatherreport.util.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LocationPermissionObserver @Inject constructor(
    @ActivityContext private val appCompatContext: Context
) : AbstractPermissionObserver(), DefaultLifecycleObserver {
    private var requestPermission: ActivityResultLauncher<Array<String>>? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (appCompatContext is AppCompatActivity) {
            requestPermission =
                appCompatContext.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                    permissions.entries.forEach {
                        granted = it.value
                        if (!it.value) {
                            return@forEach
                        }
                    }
                    if (granted) {
                        permissionsListener?.permissionGranted()
                    } else {
                        permissionsListener?.permissionDenied()
                    }
                }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (checkPermission()) {
            permissionsListener?.permissionGranted()
        } else {
            permissionsListener?.permissionDenied()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        requestPermission = null
    }

    override fun launch() {
        requestPermission?.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun checkPermission(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(
            appCompatContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return locationPermission == PackageManager.PERMISSION_GRANTED
    }
}