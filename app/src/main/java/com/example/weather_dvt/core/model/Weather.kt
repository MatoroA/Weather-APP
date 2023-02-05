package com.example.weather_dvt.core.model

import androidx.annotation.DrawableRes
import com.example.weather_dvt.R
import com.example.weather_dvt.core.network.model.weather.util.WeekDay

data class Weather(
    val day: WeekDay,
    val temperature: Int,
    val maximumTemperature: Int,
    val minimumTemperature: Int,
    val weatherId: Int,
    val isCurrent: Boolean
) {
    val weatherType: WeatherType = when (weatherId) {
        in 500..531 -> WeatherType.Rainy
        800 -> WeatherType.Sunny
        else -> WeatherType.Cloudy
    }
}

sealed class WeatherType(
    @DrawableRes val iconId: Int,
    val name: String,
    @DrawableRes val imageId: Int
) {
    object Sunny : WeatherType(
        iconId = R.drawable.clear,
        name = "Sunny",
        imageId = R.drawable.forest_sunny
    )

    object Rainy : WeatherType(
        iconId = R.drawable.rain,
        name = "Rainy",
        imageId = R.drawable.forest_rainy
    )

    object Cloudy : WeatherType(
        iconId = R.drawable.partlysunny,
        name = "Cloudy",
        imageId = R.drawable.forest_cloudy
    )
}