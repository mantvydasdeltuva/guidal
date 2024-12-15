package com.guidal.authentication.authentication

import com.guidal.data.db.models.UserModel

internal sealed interface AuthenticationUiState {
    val isNavigating: Boolean

    data class Idle(
        override val isNavigating: Boolean = false
    ) : AuthenticationUiState

    data class Loading(
        override val isNavigating: Boolean = false
    ) : AuthenticationUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false
    ) : AuthenticationUiState

    data class Success(
        val user: UserModel,
        override val isNavigating: Boolean = false
    ) : AuthenticationUiState
}

// TODO Extract string resources
internal inline fun <reified T : AuthenticationUiState> AuthenticationUiState.transformTo(
    message: String? = null,
    user: UserModel? = null
) : T {
    return when (T::class) {
        AuthenticationUiState.Idle::class -> AuthenticationUiState.Idle(
            isNavigating = this.isNavigating
        ) as T
        AuthenticationUiState.Loading::class -> AuthenticationUiState.Loading(
            isNavigating = this.isNavigating
        ) as T
        AuthenticationUiState.Error::class -> {
            requireNotNull(message) { "`message` required for state type ${T::class}" }
            AuthenticationUiState.Error(
                message = message,
                isNavigating = this.isNavigating
            ) as T
        }
        AuthenticationUiState.Success::class -> {
            requireNotNull(user) { "`user` required for state type ${T::class}" }
            AuthenticationUiState.Success(
                user = user,
                isNavigating = this.isNavigating
            ) as T
        }
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}