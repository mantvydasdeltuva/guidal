package com.guidal.network

import com.guidal.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeatherMapClient

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @OpenWeatherMapClient
    fun provideOpenWeatherMapClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url("https://api.openweathermap.org/data/3.0/onecall")
                url.parameters.appendIfNameAbsent("appid", BuildConfig.OPENWEATHERMAP_API_KEY)
            }

            install(Logging) {
                logger = Logger.SIMPLE
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
