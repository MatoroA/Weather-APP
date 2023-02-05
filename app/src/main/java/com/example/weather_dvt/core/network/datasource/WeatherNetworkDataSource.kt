package com.example.weather_dvt.core.network.datasource

import com.example.weather_dvt.core.common.utils.WeatherRequestParams

interface WeatherNetworkDataSource {
    suspend fun getForecast(params: WeatherRequestParams)

    suspend fun getCurrentWeather(params: WeatherRequestParams)
}