package com.guidal.feature.home.location.view

internal sealed interface LocationViewUiState {
    val isLoading: Boolean

    data class Idle(
        override val isLoading: Boolean = false
    ) : LocationViewUiState

    data class Loading(
        override val isLoading: Boolean = true
    ) : LocationViewUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false
    ) : LocationViewUiState

    data class Success(
        override val isLoading: Boolean = false
    ) : LocationViewUiState
}

internal inline fun <reified T : LocationViewUiState> LocationViewUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        LocationViewUiState.Idle::class -> LocationViewUiState.Idle(
            isLoading = this.isLoading
        ) as T
        LocationViewUiState.Loading::class -> LocationViewUiState.Loading(
            isLoading = this.isLoading
        ) as T
        LocationViewUiState.Error::class -> {
            if (this is LocationViewUiState.Error) {
                LocationViewUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                LocationViewUiState.Error(
                    message = message,
                    isLoading = this.isLoading
                ) as T
            }
        }
        LocationViewUiState.Success::class -> LocationViewUiState.Success(
            isLoading = this.isLoading
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}