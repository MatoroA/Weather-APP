package com.example.weather_dvt.core.model

data class AppSetting(
    val locationPermission: LocationPermission = LocationPermission.DEFAULT,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
