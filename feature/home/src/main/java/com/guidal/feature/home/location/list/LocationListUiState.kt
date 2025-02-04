package com.guidal.feature.home.location.list

import com.guidal.data.db.models.LocationModel

// LocationListUiState.kt
internal sealed interface LocationListUiState {
    val isLoading: Boolean
    val locations: List<LocationModel>

    data class Idle(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationListUiState

    data class Loading(
        override val isLoading: Boolean = true,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationListUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationListUiState

    data class Success(
        override val isLoading: Boolean = false,
        override val locations: List<LocationModel> = emptyList()
    ) : LocationListUiState
}

internal inline fun <reified T : LocationListUiState> LocationListUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        LocationListUiState.Idle::class -> LocationListUiState.Idle(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        LocationListUiState.Loading::class -> LocationListUiState.Loading(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        LocationListUiState.Error::class -> {
            if (this is LocationListUiState.Error) {
                LocationListUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                LocationListUiState.Error(
                    message = message,
                    isLoading = this.isLoading,
                    locations = this.locations.toList()
                ) as T
            }
        }
        LocationListUiState.Success::class -> LocationListUiState.Success(
            isLoading = this.isLoading,
            locations = this.locations.toList()
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}