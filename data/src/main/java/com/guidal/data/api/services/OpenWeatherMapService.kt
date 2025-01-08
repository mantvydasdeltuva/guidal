package com.guidal.data.api.services

import com.guidal.data.api.models.domain.ForecastDomainModel
import com.guidal.data.api.models.remote.ForecastRemoteModel
import com.guidal.network.OpenWeatherMapClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

// TODO KDoc
interface OpenWeatherMapService {
    suspend fun getForecast(): List<ForecastDomainModel>
}

// TODO KDoc
// TODO Unit test
internal class OpenWeatherMapServiceImpl @Inject constructor(
    @OpenWeatherMapClient private val client: HttpClient
) : OpenWeatherMapService {

    override suspend fun getForecast(): List<ForecastDomainModel> {
        return client.get {
            url {
                parameter("lat", 38.25)
                parameter("lon", 21.73)
                parameter("units", "metric")
                parameter("lang", "en")
                parameter("exclude", "current,minutely,hourly,alerts")
            }
        }
        .body<ForecastRemoteModel>()
        .toDomainModel()
    }
}