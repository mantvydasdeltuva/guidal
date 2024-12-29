package com.guidal.feature.menu.privacy

internal sealed interface PrivacyUiState {
    val isNavigating: Boolean
    val isLocationEnabled: Boolean
    val isCollectionEnabled: Boolean

    data class Idle(
        override val isNavigating: Boolean = false,
        override val isLocationEnabled: Boolean = false,
        override val isCollectionEnabled: Boolean = false
    ) : PrivacyUiState

    data class Loading(
        override val isNavigating: Boolean = false,
        override val isLocationEnabled: Boolean = false,
        override val isCollectionEnabled: Boolean = false
    ) : PrivacyUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false,
        override val isLocationEnabled: Boolean = false,
        override val isCollectionEnabled: Boolean = false
    ) : PrivacyUiState

    data class Success(
        override val isNavigating: Boolean = false,
        override val isLocationEnabled: Boolean = false,
        override val isCollectionEnabled: Boolean = false
    ) : PrivacyUiState
}

// TODO Extract string resources
internal inline fun <reified T : PrivacyUiState> PrivacyUiState.transformTo(
    message: String? = null
) : T {
    return when (T::class) {
        PrivacyUiState.Idle::class -> PrivacyUiState.Idle(
            isNavigating = this.isNavigating,
            isLocationEnabled = this.isLocationEnabled,
            isCollectionEnabled = this.isCollectionEnabled
        ) as T
        PrivacyUiState.Loading::class -> PrivacyUiState.Loading(
            isNavigating = this.isNavigating,
            isLocationEnabled = this.isLocationEnabled,
            isCollectionEnabled = this.isCollectionEnabled
        ) as T
        PrivacyUiState.Error::class -> {
            if (this is PrivacyUiState.Error) {
                PrivacyUiState.Error(
                    message = message ?: this.message,
                    isNavigating = this.isNavigating,
                    isLocationEnabled = this.isLocationEnabled,
                    isCollectionEnabled = this.isCollectionEnabled
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                PrivacyUiState.Error(
                    message = message,
                    isNavigating = this.isNavigating,
                    isLocationEnabled = this.isLocationEnabled,
                    isCollectionEnabled = this.isCollectionEnabled
                ) as T
            }
        }
        PrivacyUiState.Success::class -> PrivacyUiState.Success(
            isNavigating = this.isNavigating,
            isLocationEnabled = this.isLocationEnabled,
            isCollectionEnabled = this.isCollectionEnabled
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}