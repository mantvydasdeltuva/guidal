package com.guidal.feature.menu.main

internal sealed interface MainUiState {
    val isNavigating: Boolean

    data class Idle(
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Loading(
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false
    ) : MainUiState

    data class Success(
        override val isNavigating: Boolean = false
    ) : MainUiState
}

// TODO Extract string resources
internal inline fun <reified T : MainUiState> MainUiState.transformTo(
    message: String? = null
) : T {
    return when (T::class) {
        MainUiState.Idle::class -> MainUiState.Idle(
            isNavigating = this.isNavigating
        ) as T
        MainUiState.Loading::class -> MainUiState.Loading(
            isNavigating = this.isNavigating
        ) as T
        MainUiState.Error::class -> {
            requireNotNull(message) { "`message` required for state type ${T::class}" }
            MainUiState.Error(
                message = message,
                isNavigating = this.isNavigating
            ) as T
        }
        MainUiState.Success::class -> {
            MainUiState.Success(
                isNavigating = this.isNavigating
            ) as T
        }
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}