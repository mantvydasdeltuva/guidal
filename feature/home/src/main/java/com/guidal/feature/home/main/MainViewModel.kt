package com.guidal.feature.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.core.ui.models.UiModelWeatherWidgetItem
import com.guidal.core.ui.models.mapStringToWeatherType
import com.guidal.data.db.models.CategoryModel
import com.guidal.data.db.models.ForecastModel
import com.guidal.data.db.repositories.CategoryRepository
import com.guidal.data.db.repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

// TODO Extract string resources
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(
        value = MainUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<MainUiState.Loading>()
            }

            var categoriesResult: Result<List<CategoryModel>>? = null
            var forecastResult: Result<List<ForecastModel>>? = null

            val categoriesJob = launch {
                categoryRepository.getCategories()
                .onSuccess { categories ->
                    categoriesResult = Result.success(categories)
                }
                .onFailure { exception ->
                    categoriesResult = Result.failure(exception)
                }
            }

            val forecastJob = launch {
                forecastRepository.getForecasts()
                    .onSuccess { forecasts ->
                        forecastResult = Result.success(forecasts)
                    }
                    .onFailure { exception ->
                        forecastResult = Result.failure(exception)
                    }
            }

            // wait for tasks to complete
            categoriesJob.join()
            forecastJob.join()

            // Combine the results and update UI state
            _uiState.update {
                when {
                    categoriesResult?.isSuccess == true && forecastResult?.isSuccess == true -> {
                        it.transformTo<MainUiState.Idle>().copy(
                            forecast = forecastResult?.getOrNull()?.map { forecast ->
                                UiModelWeatherWidgetItem(
                                    day = forecast.day,
                                    value = forecast.temperature.roundToInt(),
                                    type = mapStringToWeatherType(forecast.type)
                                )
                            } ?: emptyList(),
                            categories = categoriesResult?.getOrNull()?.map { category ->
                                category.copy()
                            } ?: emptyList()
                        )
                    }
                    categoriesResult?.isSuccess == true -> {
                        it.transformTo<MainUiState.Error>(
                            message = "Failed to load forecast."
                        ).copy(
                            forecast = emptyList(),
                            categories = categoriesResult?.getOrNull()?.map { category ->
                                category.copy()
                            } ?: emptyList(),
                        )
                    }
                    forecastResult?.isSuccess == true -> {
                        it.transformTo<MainUiState.Error>(
                            message = "Failed to load categories."
                        ).copy(
                            forecast = forecastResult?.getOrNull()?.map { forecast ->
                                UiModelWeatherWidgetItem(
                                    day = forecast.day,
                                    value = forecast.temperature.toInt(),
                                    type = mapStringToWeatherType(forecast.type)
                                )
                            } ?: emptyList(),
                            categories = emptyList()
                        )
                    }
                    else -> {
                        it.transformTo<MainUiState.Error>(
                            message = "Failed to load both categories and forecast."
                        ).copy(
                            forecast = emptyList(),
                            categories = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun updateSearchState() {
        _uiState.update {
            it.transformTo<MainUiState.Idle>().copy(
                isSearchEnabled = !it.isSearchEnabled
            )
        }
    }

    fun resetState() {
        _uiState.update {
            when (it) {
                is MainUiState.Loading -> it.transformTo<MainUiState.Loading>().copy( isNavigating = false)
                else -> it.transformTo<MainUiState.Idle>().copy( isNavigating = false)
            }
        }
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<MainUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }
}