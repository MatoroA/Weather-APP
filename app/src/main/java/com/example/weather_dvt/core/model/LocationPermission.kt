package com.example.weather_dvt.core.model

enum class LocationPermission {
    DEFAULT,
    ACCEPTED,
    DENIED
}

fun String.asLocalPermission(): LocationPermission = when (this) {
    LocationPermission.ACCEPTED.name -> LocationPermission.ACCEPTED
    LocationPermission.DENIED.name -> LocationPermission.DENIED
    else -> LocationPermission.DEFAULT
}