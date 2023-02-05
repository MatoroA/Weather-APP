package com.example.weather_dvt.core.data.di

import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.core.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository
}

