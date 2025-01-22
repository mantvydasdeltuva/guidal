package com.guidal.data.api.models.remote

import com.guidal.data.api.models.domain.ForecastDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
internal data class ForecastRemoteModel(
    val daily: List<DailyWeather>
) {
    @Serializable
    data class DailyWeather(
        @SerialName("dt") val timestamp: Long,
        @SerialName("temp") val temperature: Temperature,
        val weather: List<WeatherDetail>,
        val summary: String
    ) {
        @Serializable
        data class Temperature(
            val day: Double,
            val min: Double,
            val max: Double
        )

        @Serializable
        data class WeatherDetail(
            val id: Int,
            val main: String,
            val description: String
        )
    }

    fun toDomainModel(): List<ForecastDomainModel> {
        return daily.take(7).map { dailyWeather ->
            ForecastDomainModel(
                day = dailyWeather.timestamp.toDayOfWeek(),
                temperature = dailyWeather.temperature.max,
                type = mapWeatherIdToType(dailyWeather.weather.firstOrNull()?.id)
            )
        }
    }

    private fun Long.toDayOfWeek(): String {
        val date = Date(this * 1000)
        val dateFormat = SimpleDateFormat("EEE", Locale.US)
        return dateFormat.format(date)
    }

    private fun mapWeatherIdToType(id: Int?): String {
        return when (id) {
            in 200..232 -> "Thunder"
            in 300..321 -> "Rainy"
            in 500..531 -> "Rainy"
            in 600..622 -> "Snowy"
            in 701..781 -> "Partly Cloudy"
            800 -> "Sunny"
            801 -> "Partly Cloudy"
            in 802..804 -> "Cloudy"
            else -> "Unknown"
        }
    }
}

