package com.example.weather_dvt.core.datastore.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.weather_dvt.core.model.AppSetting
import com.example.weather_dvt.core.model.LocationDetails
import com.example.weather_dvt.core.model.LocationPermission
import com.example.weather_dvt.core.model.asLocalPermission
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"
private const val PERMISSION = "permission"

class UserPreferencesImpl @Inject constructor(private val preferences: SharedPreferences) :
    UserPreferences {

    private val _appSetting = MutableStateFlow(AppSetting())
    private val appSetting: StateFlow<AppSetting> = _appSetting

    init {
        val latitude = preferences.getString(LATITUDE, "0.0")
        val longitude = preferences.getString(LONGITUDE, "0.0")
        val permission = preferences.getString(PERMISSION, LocationPermission.DEFAULT.name)

        _appSetting.update { state ->
            state.copy(
                latitude = latitude?.toDouble() ?: 0.0,
                longitude = longitude?.toDouble() ?: 0.0,
                locationPermission = permission?.asLocalPermission() ?: LocationPermission.DEFAULT
            )
        }
    }

    override fun getAppState(): Flow<AppSetting> {
        return appSetting
    }

    override fun setLocation(locationDetails: LocationDetails) {
        preferences.edit {
            putString(LATITUDE, locationDetails.latitude.toString())
            putString(LONGITUDE, locationDetails.longitude.toString())
        }

        _appSetting.update { state ->
            state.copy(latitude = locationDetails.latitude, longitude = locationDetails.longitude)
        }
    }

    override fun setPermissionStatus(status: LocationPermission) {
        preferences.edit {
            putString(PERMISSION, status.name)
        }

        _appSetting.update { state ->
            state.copy(locationPermission = status)
        }
    }
}