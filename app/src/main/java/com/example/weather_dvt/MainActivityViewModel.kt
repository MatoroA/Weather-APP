package com.example.weather_dvt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.core.datastore.repository.UserPreferences
import com.example.weather_dvt.core.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    repository: WeatherRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = combine(
        repository.getCurrentWeather(),
        userPreferences.getAppState()
    ) { currentWeather, appSetting ->

        MainActivityUiState(
            themeType = currentWeather.asAppTheme(),
            appSetting = appSetting
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun updateUserLocation(locationDetails: LocationDetails) {
        userPreferences.setLocation(locationDetails)
    }

    fun updatePermission(userPermission: LocationPermission) {
        userPreferences.setPermissionStatus(userPermission)
    }
}

fun Weather?.asAppTheme(): WeatherAppThemeType {
    return when (this?.weatherType) {
        WeatherType.Sunny -> WeatherAppThemeType.SUNNY
        WeatherType.Rainy -> WeatherAppThemeType.RAINY
        WeatherType.Cloudy -> WeatherAppThemeType.CLOUDY
        else -> WeatherAppThemeType.DEFAULT
    }
}

data class MainActivityUiState(
    val themeType: WeatherAppThemeType = WeatherAppThemeType.DEFAULT,
    val appSetting: AppSetting = AppSetting()
)