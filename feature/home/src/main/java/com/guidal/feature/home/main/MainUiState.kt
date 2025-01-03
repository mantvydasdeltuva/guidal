package com.guidal.feature.home.main

import com.guidal.core.ui.models.UiModelWeatherWidgetItem
import com.guidal.data.db.models.CategoryModel

internal sealed interface MainUiState {
    val forecast: List<UiModelWeatherWidgetItem>
    val categories: List<CategoryModel>
    val isSearchEnabled: Boolean
    val isNavigating: Boolean

    data class Idle(
        override val forecast: List<UiModelWeatherWidgetItem> = emptyList(),
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false,
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Loading(
        override val forecast: List<UiModelWeatherWidgetItem> = emptyList(),
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false,
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Error(
        val message: String,
        override val forecast: List<UiModelWeatherWidgetItem> = emptyList(),
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false,
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Success(
        override val forecast: List<UiModelWeatherWidgetItem> = emptyList(),
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false,
        override val isNavigating: Boolean = false
    ) : MainUiState
}

// TODO Extract string resources
internal inline fun <reified T : MainUiState> MainUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        MainUiState.Idle::class -> MainUiState.Idle(
            forecast = this.forecast.toList(),
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled,
            isNavigating = this.isNavigating
        ) as T
        MainUiState.Loading::class -> MainUiState.Loading(
            forecast = this.forecast.toList(),
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled,
            isNavigating = this.isNavigating
        ) as T
        MainUiState.Error::class -> {
            if (this is MainUiState.Error) {
                MainUiState.Error(
                    message = message ?: this.message,
                    forecast = this.forecast.toList(),
                    categories = this.categories.toList(),
                    isSearchEnabled = this.isSearchEnabled,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                MainUiState.Error(
                    message = message,
                    forecast = this.forecast.toList(),
                    categories = this.categories.toList(),
                    isSearchEnabled = this.isSearchEnabled,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        MainUiState.Success::class -> MainUiState.Success(
            forecast = this.forecast.toList(),
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled,
            isNavigating = this.isNavigating
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}