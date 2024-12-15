package com.guidal.authentication.login

import com.guidal.data.db.models.UserModel

internal sealed interface LoginUiState {
    // Text field values
    val email: String
    val password: String

    // Text field error states
    val isEmailError: Boolean
    val isPasswordError: Boolean

    // Text field error messages
    val emailError: String
    val passwordError: String

    data class Idle(
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = ""
    ) : LoginUiState

    data class Loading(
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = ""
    ) : LoginUiState

    data class Error(
        val message: String,
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = ""
    ) : LoginUiState

    data class Success(
        val user: UserModel,
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = ""
    ) : LoginUiState
}

// TODO Extract string resources
internal inline fun <reified T : LoginUiState> LoginUiState.transformTo(
    message: String? = null,
    user: UserModel? = null
) : T {
    return when (T::class) {
        LoginUiState.Idle::class -> LoginUiState.Idle(
            email = this.email,
            isEmailError = this.isEmailError,
            emailError = this.emailError,
            password = this.password,
            isPasswordError = this.isPasswordError,
            passwordError = this.passwordError
        ) as T
        LoginUiState.Loading::class -> LoginUiState.Loading(
            email = this.email,
            isEmailError = this.isEmailError,
            emailError = this.emailError,
            password = this.password,
            isPasswordError = this.isPasswordError,
            passwordError = this.passwordError
        ) as T
        LoginUiState.Error::class -> {
            requireNotNull(message) { "`message` required for state type ${T::class}" }
            LoginUiState.Error(
                message = message,
                email = this.email,
                isEmailError = this.isEmailError,
                emailError = this.emailError,
                password = this.password,
                isPasswordError = this.isPasswordError,
                passwordError = this.passwordError
            ) as T
        }
        LoginUiState.Success::class -> {
            requireNotNull(user) { "`user` required for state type ${T::class}" }
            LoginUiState.Success(
                user = user,
                email = this.email,
                isEmailError = this.isEmailError,
                emailError = this.emailError,
                password = this.password,
                isPasswordError = this.isPasswordError,
                passwordError = this.passwordError
            ) as T
        }
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}