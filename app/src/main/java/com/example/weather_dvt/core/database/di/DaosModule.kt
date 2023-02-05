package com.example.weather_dvt.core.database.di

import com.example.weather_dvt.core.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao()
}