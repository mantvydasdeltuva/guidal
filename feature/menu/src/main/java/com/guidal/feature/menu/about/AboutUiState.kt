package com.guidal.feature.menu.about

internal sealed interface AboutUiState {
    val version: String
    val isNavigating: Boolean

    data class Idle(
        override val version: String = "",
        override val isNavigating: Boolean = false
    ) : AboutUiState

    data class Loading(
        override val version: String = "",
        override val isNavigating: Boolean = false
    ) : AboutUiState

    data class Error(
        val message: String,
        override val version: String = "",
        override val isNavigating: Boolean = false
    ) : AboutUiState

    data class Success(
        override val version: String = "",
        override val isNavigating: Boolean = false
    ) : AboutUiState
}

// TODO Extract string resources
internal inline fun <reified T : AboutUiState> AboutUiState.transformTo(
    message: String? = null
) : T {
    return when (T::class) {
        AboutUiState.Idle::class -> AboutUiState.Idle(
            version = this.version,
            isNavigating = this.isNavigating
        ) as T
        AboutUiState.Loading::class -> AboutUiState.Loading(
            version = this.version,
            isNavigating = this.isNavigating
        ) as T
        AboutUiState.Error::class -> {
            if (this is AboutUiState.Error) {
                AboutUiState.Error(
                    message = message ?: this.message,
                    version = this.version,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                AboutUiState.Error(
                    message = message,
                    version = this.version,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        AboutUiState.Success::class -> AboutUiState.Success(
            version = this.version,
            isNavigating = this.isNavigating
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}