package com.guidal.core.ui.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.guidal.core.ui.theme.GuidalIcons

data class UiModelWeatherWidgetItem (
    val day: String,
    val value: Int,
    val type: WeatherType
)

// TODO Fix icon colors on dark mode
enum class WeatherType(
    val icon: ImageVector
) {
    Sunny(icon = GuidalIcons.Weather.Sunny),                // Only sun
    Cloudy(icon = GuidalIcons.Weather.Cloudy),              // Only clouds
    PartlyCloudy(icon = GuidalIcons.Weather.PartlyCloudy),  // Clouds and sun
    Rainy(icon = GuidalIcons.Weather.Rainy),                // Only rain
    Thunder(icon = GuidalIcons.Weather.Thunder)             // Lightning and clouds
}