package com.guidal.authentication.signup

import com.guidal.data.db.models.UserModel

internal sealed interface SignupUiState {
    // Text field values
    val name: String
    val surname: String
    val email: String
    val password: String
    val confirmPassword: String

    // Text field error states
    val isNameError: Boolean
    val isEmailError: Boolean
    val isPasswordError: Boolean
    val isConfirmPasswordError: Boolean

    // Text field error messages
    val nameError: String
    val emailError: String
    val passwordError: String
    val confirmPasswordError: String

    data class Idle(
        override val name: String = "",
        override val isNameError: Boolean = false,
        override val nameError: String = "",
        override val surname: String = "",
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = "",
        override val confirmPassword: String = "",
        override val isConfirmPasswordError: Boolean = false,
        override val confirmPasswordError: String = ""
    ) : SignupUiState

    data class Loading(
        override val name: String = "",
        override val isNameError: Boolean = false,
        override val nameError: String = "",
        override val surname: String = "",
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = "",
        override val confirmPassword: String = "",
        override val isConfirmPasswordError: Boolean = false,
        override val confirmPasswordError: String = ""
    ) : SignupUiState

    data class Error(
        val message: String,
        override val name: String = "",
        override val isNameError: Boolean = false,
        override val nameError: String = "",
        override val surname: String = "",
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = "",
        override val confirmPassword: String = "",
        override val isConfirmPasswordError: Boolean = false,
        override val confirmPasswordError: String = ""
    ) : SignupUiState

    data class Success(
        val user: UserModel,
        override val name: String = "",
        override val isNameError: Boolean = false,
        override val nameError: String = "",
        override val surname: String = "",
        override val email: String = "",
        override val isEmailError: Boolean = false,
        override val emailError: String = "",
        override val password: String = "",
        override val isPasswordError: Boolean = false,
        override val passwordError: String = "",
        override val confirmPassword: String = "",
        override val isConfirmPasswordError: Boolean = false,
        override val confirmPasswordError: String = ""
    ) : SignupUiState
}

// TODO Extract string resources
internal inline fun <reified T : SignupUiState> SignupUiState.transformTo(
    message: String? = null,
    user: UserModel? = null
) : T {
    return when (T::class) {
        SignupUiState.Idle::class -> SignupUiState.Idle(
            name = this.name,
            isNameError = this.isNameError,
            nameError = this.nameError,
            surname = this.surname,
            email = this.email,
            isEmailError = this.isEmailError,
            emailError = this.emailError,
            password = this.password,
            isPasswordError = this.isPasswordError,
            passwordError = this.passwordError,
            confirmPassword = this.confirmPassword,
            isConfirmPasswordError = this.isConfirmPasswordError,
            confirmPasswordError = this.confirmPasswordError
        ) as T
        SignupUiState.Loading::class -> SignupUiState.Loading(
            name = this.name,
            isNameError = this.isNameError,
            nameError = this.nameError,
            surname = this.surname,
            email = this.email,
            isEmailError = this.isEmailError,
            emailError = this.emailError,
            password = this.password,
            isPasswordError = this.isPasswordError,
            passwordError = this.passwordError,
            confirmPassword = this.confirmPassword,
            isConfirmPasswordError = this.isConfirmPasswordError,
            confirmPasswordError = this.confirmPasswordError
        ) as T
        SignupUiState.Error::class -> {
            requireNotNull(message) { "message required for state type ${T::class.simpleName}" }
            SignupUiState.Error(
                message = message,
                name = this.name,
                isNameError = this.isNameError,
                nameError = this.nameError,
                surname = this.surname,
                email = this.email,
                isEmailError = this.isEmailError,
                emailError = this.emailError,
                password = this.password,
                isPasswordError = this.isPasswordError,
                passwordError = this.passwordError,
                confirmPassword = this.confirmPassword,
                isConfirmPasswordError = this.isConfirmPasswordError,
                confirmPasswordError = this.confirmPasswordError
            ) as T
        }
        SignupUiState.Success::class -> {
            requireNotNull(user) { "user required for state type ${T::class.simpleName}" }
            SignupUiState.Success(
                user = user,
                name = this.name,
                isNameError = this.isNameError,
                nameError = this.nameError,
                surname = this.surname,
                email = this.email,
                isEmailError = this.isEmailError,
                emailError = this.emailError,
                password = this.password,
                isPasswordError = this.isPasswordError,
                passwordError = this.passwordError,
                confirmPassword = this.confirmPassword,
                isConfirmPasswordError = this.isConfirmPasswordError,
                confirmPasswordError = this.confirmPasswordError
            ) as T
        }
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class.simpleName}")
    }
}