package com.example.weather_dvt.core.common.result.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.weather_dvt.core.model.WeatherAppThemeType

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.LightGray

    /* Other Default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val CloudyColorPalette = darkColors(
    primary = Cloudy,
    primaryVariant = Cloudy,
    secondary = Cloudy,
    background = Cloudy
)

private val SunnyColorPalette = darkColors(
    primary = Sunny,
    primaryVariant = Sunny,
    secondary = Sunny,
    background = Sunny
)

private val RainyColorPalette = darkColors(
    primary = Rainy,
    primaryVariant = Rainy,
    secondary = Rainy,
    background = Rainy
)

@Composable
fun WeatherDVTTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeType: WeatherAppThemeType = WeatherAppThemeType.DEFAULT,
    content: @Composable () -> Unit
) {
    val colors = when (themeType) {
        WeatherAppThemeType.CLOUDY -> CloudyColorPalette
        WeatherAppThemeType.RAINY -> RainyColorPalette
        WeatherAppThemeType.SUNNY -> SunnyColorPalette
        else -> LightColorPalette
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
