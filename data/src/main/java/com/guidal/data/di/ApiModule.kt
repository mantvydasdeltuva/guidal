package com.guidal.data.di

import com.guidal.data.api.services.OpenWeatherMapService
import com.guidal.data.api.services.OpenWeatherMapServiceImpl
import com.guidal.network.OpenWeatherMapClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

// TODO KDoc
@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    /*
     *  OpenWeatherMap API
     */

    @Provides
    @Singleton
    internal fun provideOpenWeatherMapService(
        @OpenWeatherMapClient client: HttpClient
    ): OpenWeatherMapService {
        return OpenWeatherMapServiceImpl(client)
    }
}