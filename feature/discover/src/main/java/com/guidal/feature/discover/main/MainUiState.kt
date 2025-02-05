package com.guidal.feature.discover.main

import com.guidal.data.db.models.LocationModel

// MainUiState.kt
internal sealed interface MainUiState {
    val isLoading: Boolean
    val locations: List<LocationModel>

    data class Idle(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : MainUiState

    data class Loading(
        override val isLoading: Boolean = true,
        override val locations: List<LocationModel> = emptyList()
    ) : MainUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : MainUiState

    data class Success(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : MainUiState
}

internal inline fun <reified T : MainUiState> MainUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        MainUiState.Idle::class -> MainUiState.Idle(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        MainUiState.Loading::class -> MainUiState.Loading(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        MainUiState.Error::class -> {
            if (this is MainUiState.Error) {
                MainUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                MainUiState.Error(
                    message = message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()
                ) as T
            }
        }
        MainUiState.Success::class -> MainUiState.Success(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}