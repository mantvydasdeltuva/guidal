package com.guidal.feature.menu.profile

import com.guidal.data.db.models.UserModel

internal sealed interface ProfileUiState {
    val isNavigating: Boolean

    data class Idle(
        val user: UserModel,
        override val isNavigating: Boolean = false
    ) : ProfileUiState

    data class Loading(
        override val isNavigating: Boolean = false
    ) : ProfileUiState

    data class Error(
        val message: String,
        override val isNavigating: Boolean = false
    ) : ProfileUiState

    data class Success(
        override val isNavigating: Boolean = false
    ) : ProfileUiState
}

// TODO Extract string resources
internal inline fun <reified T : ProfileUiState> ProfileUiState.transformTo(
    user: UserModel? = null,
    message: String? = null
) : T {
    return when (T::class) {
        ProfileUiState.Idle::class -> {
            if (this is ProfileUiState.Idle) {
                ProfileUiState.Idle(
                    user = user ?: this.user,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(user) { "`user` required for state type ${T::class}" }
                ProfileUiState.Idle(
                    user = user,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        ProfileUiState.Loading::class -> ProfileUiState.Loading(
            isNavigating = this.isNavigating
        ) as T
        ProfileUiState.Error::class -> {
            if (this is ProfileUiState.Error) {
                ProfileUiState.Error(
                    message = message ?: this.message,
                    isNavigating = this.isNavigating
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                ProfileUiState.Error(
                    message = message,
                    isNavigating = this.isNavigating
                ) as T
            }
        }
        ProfileUiState.Success::class -> {
            ProfileUiState.Success(
                isNavigating = this.isNavigating
            ) as T
        }
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}