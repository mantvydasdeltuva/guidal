package com.guidal.feature.menu.settings

internal sealed interface SettingsUiState {
    val isNavigating: Boolean

    data class Idle(
        override val isNavigating: Boolean = false
    ) : SettingsUiState

    data class Loading(
        override val isNavigating: Boolean = false
    ) : SettingsUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false
    ) : SettingsUiState

    data class Success(
        override val isNavigating: Boolean = false
    ) : SettingsUiState
}

// TODO Extract string resources
internal inline fun <reified T : SettingsUiState> SettingsUiState.transformTo(
    message: String? = null
) : T {
    return when (T::class) {
        SettingsUiState.Idle::class -> SettingsUiState.Idle(
            isNavigating = this.isNavigating
        ) as T
        SettingsUiState.Loading::class -> SettingsUiState.Loading(
            isNavigating = this.isNavigating
        ) as T
        SettingsUiState.Error::class -> {
            if (this is SettingsUiState.Error) {
                SettingsUiState.Error(
                    message = message ?: this.message,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                SettingsUiState.Error(
                    message = message,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        SettingsUiState.Success::class -> SettingsUiState.Success(
            isNavigating = this.isNavigating
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}