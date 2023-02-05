package com.example.weather_dvt.feature.weather.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.weather_dvt.feature.weather.WeatherRoute


const val weatherRoute: String = "weather_route"


fun NavGraphBuilder.weatherScreen() {
    composable(route = weatherRoute) {
        WeatherRoute()
    }
}