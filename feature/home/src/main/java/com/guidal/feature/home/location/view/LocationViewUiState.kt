package com.guidal.feature.home.location.view

import com.guidal.data.db.models.LocationModel

internal sealed interface LocationViewUiState {
    val isLoading: Boolean
    val location: LocationModel?

    data class Idle(
        override val isLoading: Boolean = false,
        override val location: LocationModel? = null
    ) : LocationViewUiState

    data class Loading(
        override val isLoading: Boolean = true,
        override val location: LocationModel? = null
    ) : LocationViewUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false,
        override val location: LocationModel? = null
    ) : LocationViewUiState

    data class Success(
        override val isLoading: Boolean = false,
        override val location: LocationModel? = null
    ) : LocationViewUiState
}

internal inline fun <reified T : LocationViewUiState> LocationViewUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        LocationViewUiState.Idle::class -> LocationViewUiState.Idle(
            isLoading = this.isLoading,
            location = this.location?.copy()
        ) as T
        LocationViewUiState.Loading::class -> LocationViewUiState.Loading(
            isLoading = this.isLoading,
            location = this.location?.copy()
        ) as T
        LocationViewUiState.Error::class -> {
            if (this is LocationViewUiState.Error) {
                LocationViewUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading,
                    location = this.location?.copy()
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                LocationViewUiState.Error(
                    message = message,
                    isLoading = this.isLoading,
                    location = this.location?.copy()
                ) as T
            }
        }
        LocationViewUiState.Success::class -> LocationViewUiState.Success(
            isLoading = this.isLoading,
            location = this.location?.copy()
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}