package com.example.weather_dvt.core.datastore.repository

import com.example.weather_dvt.core.model.AppSetting
import com.example.weather_dvt.core.model.LocationDetails
import com.example.weather_dvt.core.model.LocationPermission
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getAppState(): Flow<AppSetting>

    fun setLocation(locationDetails: LocationDetails)

    fun setPermissionStatus(status: LocationPermission)

}