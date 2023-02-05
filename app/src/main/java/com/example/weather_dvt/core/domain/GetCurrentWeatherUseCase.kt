package com.example.weather_dvt.core.domain

import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.core.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(): Flow<Weather?> {
        return repository.getCurrentWeather()
    }
}