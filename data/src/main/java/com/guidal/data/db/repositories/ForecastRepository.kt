package com.guidal.data.db.repositories

import android.app.Application
import com.guidal.data.api.services.NotificationService
import com.guidal.data.api.services.OpenWeatherMapService
import com.guidal.data.db.daos.ForecastDao
import com.guidal.data.db.entities.ForecastEntity
import com.guidal.data.db.models.ForecastModel
import com.guidal.data.utils.DatabaseOperation
import com.guidal.data.utils.DatabaseOperationUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

// TODO KDoc
interface ForecastRepository {
    suspend fun getForecasts(): DatabaseOperation<List<ForecastModel>>
}

// TODO KDoc
// TODO Unit test
internal class ForecastRepositoryImpl @Inject constructor(
    private val forecastDao: ForecastDao,
    private val openWeatherMapService: OpenWeatherMapService,
    private val databaseOperationUtils: DatabaseOperationUtils,
    private val app: Application,
) : ForecastRepository {
    override suspend fun getForecasts(): DatabaseOperation<List<ForecastModel>> {
        return databaseOperationUtils.safeDatabaseOperation {
            // Fetch data from database
            val forecastModelList = forecastDao.getForecastsModel()

            // Check if data is not missing/outdated
            if (forecastModelList.isNotEmpty() && isTimestampOfCurrentDay(forecastModelList[0].timestamp)) {
                // Return data from database
                return@safeDatabaseOperation forecastModelList
            }

            return@safeDatabaseOperation try {
                // Fetch new data from API
                val forecastEntityList = openWeatherMapService.getForecast().map {
                    ForecastEntity(
                        day = it.day,
                        temperature = it.temperature,
                        type = it.type,
                        timestamp = getCurrentDayStartTimestamp()
                    )
                }

                val notificationService = NotificationService(app.applicationContext)
                val title = "Today's Weather"
                val textMap = mapOf(
                    "Thunder" to "A thunderstorm is expected today. Stay safe!",
                    "Rainy" to "Expect heavy rain today. Don't forget your umbrella!",
                    "Snowy" to "Snowfall is expected today. Drive carefully!",
                    "Partly Cloudy" to "Partly cloudy skies today. Enjoy the mild weather.",
                    "Sunny" to "Expect clear skies and sunshine all day!",
                    "Cloudy" to "Cloudy weather today, but no rain expected.",
                    "Unknown" to "Weather data is unavailable. Please check again later."
                )

                notificationService.showNotification(
                    title = title,
                    text = textMap[forecastEntityList[0].type] ?: "Weather data is unavailable. Please check again later."
                )

                // Save data to database
                forecastDao.insertAll(forecastEntityList)

                // Return data from database
                forecastDao.getForecastsModel()
            } catch (e: Exception) {
                forecastModelList.ifEmpty {
                    throw RuntimeException(
                        "No forecast data available and failed to fetch from API",
                        e
                    )
                }
            }
        }
    }

    private fun isTimestampOfCurrentDay(timestamp: Long): Boolean {
        // Convert the timestamp to LocalDate
        val dateOfTimestamp = Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        // Get the current date
        val currentDate = LocalDate.now()

        // Compare the two dates
        return dateOfTimestamp.isEqual(currentDate)
    }

    private fun getCurrentDayStartTimestamp(): Long {
        return LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}

