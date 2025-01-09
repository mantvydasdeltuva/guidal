package com.guidal.feature.menu.support

internal sealed interface SupportUiState {
    val isNavigating: Boolean

    data class Idle(
        override val isNavigating: Boolean = false
    ) : SupportUiState

    data class Loading(
        override val isNavigating: Boolean = false
    ) : SupportUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false
    ) : SupportUiState

    data class Success(
        override val isNavigating: Boolean = false
    ) : SupportUiState
}

// TODO Extract string resources
internal inline fun <reified T : SupportUiState> SupportUiState.transformTo(
    message: String? = null
) : T {
    return when (T::class) {
        SupportUiState.Idle::class -> SupportUiState.Idle(
            isNavigating = this.isNavigating
        ) as T
        SupportUiState.Loading::class -> SupportUiState.Loading(
            isNavigating = this.isNavigating
        ) as T
        SupportUiState.Error::class -> {
            if (this is SupportUiState.Error) {
                SupportUiState.Error(
                    message = message ?: this.message,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                SupportUiState.Error(
                    message = message,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        SupportUiState.Success::class -> SupportUiState.Success(
            isNavigating = this.isNavigating
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}