package com.example.weather_dvt.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_dvt.core.common.utils.WeatherRequestParams
import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.core.datastore.repository.UserPreferences
import com.example.weather_dvt.core.domain.GetWeatherDataUseCase
import com.example.weather_dvt.core.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherDataUseCase: GetWeatherDataUseCase,
    private val repository: WeatherRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val weatherDataState: StateFlow<WeatherDataUi> = weatherDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WeatherDataUi()
        )


    init {
        getCurrentLocationWeather()
    }

    private fun getCurrentLocationWeather() {
        viewModelScope.launch {
            userPreferences.getAppState().collect { location ->
                repository.getWeather(
                    WeatherRequestParams(
                        longitude = location.longitude.toString(),
                        latitude = location.latitude.toString()
                    )
                )
            }
        }
    }
}

data class WeatherDataUi(
    val currentWeather: Weather? = null,
    val forecast: List<Weather> = emptyList()
)