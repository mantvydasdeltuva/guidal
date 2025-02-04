package com.guidal.feature.home.location.list

// LocationListUiState.kt
internal sealed interface LocationListUiState {
    val isLoading: Boolean

    data class Idle(
        override val isLoading: Boolean = false
    ) : LocationListUiState

    data class Loading(
        override val isLoading: Boolean = true
    ) : LocationListUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false
    ) : LocationListUiState

    data class Success(
        override val isLoading: Boolean = false
    ) : LocationListUiState
}

internal inline fun <reified T : LocationListUiState> LocationListUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        LocationListUiState.Idle::class -> LocationListUiState.Idle(
            isLoading = this.isLoading
        ) as T
        LocationListUiState.Loading::class -> LocationListUiState.Loading(
            isLoading = this.isLoading
        ) as T
        LocationListUiState.Error::class -> {
            if (this is LocationListUiState.Error) {
                LocationListUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                LocationListUiState.Error(
                    message = message,
                    isLoading = this.isLoading
                ) as T
            }
        }
        LocationListUiState.Success::class -> LocationListUiState.Success(
            isLoading = this.isLoading
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}