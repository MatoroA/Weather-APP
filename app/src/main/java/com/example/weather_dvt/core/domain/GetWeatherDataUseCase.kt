package com.example.weather_dvt.core.domain

import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.feature.weather.WeatherDataUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<WeatherDataUi> {
        return combine(
            weatherRepository.getCurrentWeather(),
            weatherRepository.getWeatherData()
        ) { currentWeather, forecast ->
            WeatherDataUi(currentWeather = currentWeather, forecast = forecast)
        }
    }
}