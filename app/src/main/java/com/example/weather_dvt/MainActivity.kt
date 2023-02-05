package com.example.weather_dvt

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather_dvt.core.common.result.theme.WeatherDVTTheme
import com.example.weather_dvt.core.model.LocationDetails
import com.example.weather_dvt.core.model.LocationPermission
import com.example.weather_dvt.feature.weather.WeatherRoute
import com.google.accompanist.permissions.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.weather_dvt.core.model.LocationPermission.*
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState by mutableStateOf(MainActivityUiState())

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach {
                    uiState = it
                }.collect()
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherDVTTheme(themeType = uiState.themeType) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (uiState.appSetting.locationPermission) {
                        ACCEPTED -> {
                            // show ui
                            WeatherRoute()
                        }
                        DEFAULT -> {
                            RequestLocationPermission(
                                context = this,
                                permissionStatus = DEFAULT
                            )
                        }
                        DENIED -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Denied")
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestLocationPermission(
        context: Context,
        permission: String = ACCESS_FINE_LOCATION,
        permissionStatus: LocationPermission,
    ) {
        val locationPermission = rememberPermissionState(permission = permission)

        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        val shouldOpenPermissionWindow = permissionStatus != ACCEPTED
        if (shouldOpenPermissionWindow) {
            SideEffect {
                locationPermission.launchPermissionRequest()
            }
        }

        if (locationPermission.status.isGranted && isPermissionGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.updatePermission(ACCEPTED)
                    viewModel.updateUserLocation(LocationDetails(it.latitude, it.longitude))
                }
            }
        } else if (locationPermission.status.shouldShowRationale) {
            viewModel.updatePermission(DENIED)
        }
    }

}
