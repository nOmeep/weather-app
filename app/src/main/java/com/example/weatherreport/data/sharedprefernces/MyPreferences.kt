package com.example.weatherreport.data.sharedprefernces

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.example.weatherreport.util.constants.Constants

@Singleton
class MyPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)

    fun saveLocation(latitude: Float, longitude: Float) {
        prefs.edit().apply {
            putString(Constants.SHARED_PREF_LOCATION, "$latitude,$longitude")
            apply()
        }
    }

    fun removeSavedLocation() {
        prefs.edit().remove(Constants.SHARED_PREF_LOCATION).apply()
    }

    fun getLocationOrNull(): String? =
        prefs.getString(Constants.SHARED_PREF_LOCATION, null)

    fun saveLastShownCity(name: String?) {
        prefs.edit().apply {
            putString(Constants.SHARED_PREF_CITY_KEY, name)
            apply()
        }
    }

    fun getLastShownCityOrNull(): String? =
        prefs.getString(Constants.SHARED_PREF_CITY_KEY, null)
}