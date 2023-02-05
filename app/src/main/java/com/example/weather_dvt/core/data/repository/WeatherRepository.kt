package com.example.weather_dvt.core.data.repository

import com.example.weather_dvt.core.common.utils.WeatherRequestParams
import com.example.weather_dvt.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(params: WeatherRequestParams)

    fun getWeatherData(): Flow<List<Weather>>

    fun getCurrentWeather(): Flow<Weather?>
}