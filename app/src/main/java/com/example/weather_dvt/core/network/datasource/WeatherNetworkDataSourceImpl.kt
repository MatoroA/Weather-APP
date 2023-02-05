package com.example.weather_dvt.core.network.datasource

import com.example.weather_dvt.core.common.utils.WeatherRequestParams
import com.example.weather_dvt.core.database.dao.WeatherDao
import com.example.weather_dvt.core.network.ApiInterface
import com.example.weather_dvt.core.network.model.weather.NetworkForecastResponse
import com.example.weather_dvt.core.network.model.weather.asListOfWeatherEntity
import com.example.weather_dvt.core.network.model.weather.current.asWeatherEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherDao: WeatherDao,
) : WeatherNetworkDataSource {
    override suspend fun getForecast(
        params: WeatherRequestParams
    ) {
        withContext(ioDispatcher) {
            try {
                val response = apiInterface.getForecast(
                    latitude = params.latitude,
                    longitude = params.longitude
                )
                weatherDao.deletePreviousWeatherRecords(response.city.cityId)
                convertForecastResponseToEntityAndInsert(response)
            } catch (err: Exception) {
                Timber.e(err.message)
            }
        }
    }

    private fun convertForecastResponseToEntityAndInsert(forecastResponse: NetworkForecastResponse) {
        val weatherEntities = forecastResponse.asListOfWeatherEntity()
        weatherDao.insertWeatherForecast(weatherEntities)
    }

    override suspend fun getCurrentWeather(params: WeatherRequestParams) {
        withContext(ioDispatcher) {
            try {
                val networkWeatherResponse = apiInterface.getCurrentWeather(
                    longitude = params.longitude,
                    latitude = params.latitude
                )
                val weatherEntity = networkWeatherResponse.asWeatherEntity()
                weatherDao.deletePreviousWeatherRecords(weatherEntity.cityId, true)
                weatherDao.insertCurrentWeather(weatherEntity)
                Timber.d("is this current weather: ${weatherEntity.isCurrentDayWeather}")
            } catch (err: Exception) {
                Timber.d(err.message)
            }
        }
    }
}