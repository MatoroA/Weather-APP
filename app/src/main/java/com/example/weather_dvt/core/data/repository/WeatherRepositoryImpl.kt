package com.example.weather_dvt.core.data.repository

import com.example.weather_dvt.core.common.utils.WeatherRequestParams
import com.example.weather_dvt.core.database.dao.WeatherDao
import com.example.weather_dvt.core.database.model.WeatherEntity
import com.example.weather_dvt.core.database.model.asExternalModel
import com.example.weather_dvt.core.model.Weather
import com.example.weather_dvt.core.network.datasource.WeatherNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherNetworkDataSource,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    override suspend fun getWeather(params: WeatherRequestParams) {
        dataSource.getForecast(params)
        dataSource.getCurrentWeather(params)
    }

    override fun getWeatherData(): Flow<List<Weather>> {
        return weatherDao.getWeatherForecast().map {
            if (it.isEmpty()) {
                emptyList()
            } else {
                it.map(WeatherEntity::asExternalModel)
            }
        }
    }

    override fun getCurrentWeather(): Flow<Weather?> {
        return weatherDao.getCurrentWeather().map {
            it?.asExternalModel()
        }
    }
}