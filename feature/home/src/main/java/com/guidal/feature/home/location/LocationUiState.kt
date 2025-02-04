package com.guidal.feature.home.location

import com.guidal.data.db.models.LocationModel

// LocationUiState.kt
internal sealed interface LocationUiState {
    val isLoading: Boolean
    val locations: List<LocationModel>

    data class Idle(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationUiState

    data class Loading(
        override val isLoading: Boolean = true,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationUiState

    data class Success(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationUiState
}

internal inline fun <reified T : LocationUiState> LocationUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        LocationUiState.Idle::class -> LocationUiState.Idle(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        LocationUiState.Loading::class -> LocationUiState.Loading(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        LocationUiState.Error::class -> {
            if (this is LocationUiState.Error) {
                LocationUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()

                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                LocationUiState.Error(
                    message = message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()
                ) as T
            }
        }
        LocationUiState.Success::class -> LocationUiState.Success(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}