package com.guidal.authentication.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.data.db.models.UserModel
import com.guidal.data.db.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract string resources
@HiltViewModel
internal class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SignupUiState>(
        value = SignupUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun signup() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<SignupUiState.Loading>()
            }

            // TODO Remove
            delay(2000)

            // User input validation
            val validationResult = validate()
            val errors = listOf(
                validationResult.isNameError,
                validationResult.isEmailError,
                validationResult.isPasswordError,
                validationResult.isConfirmPasswordError
            )
            if (errors.any { it }) {
                _uiState.update {
                    it.transformTo<SignupUiState.Idle>().copy(
                        isNameError = validationResult.isNameError,
                        nameError = validationResult.nameError,
                        isEmailError = validationResult.isEmailError,
                        emailError = validationResult.emailError,
                        isPasswordError = validationResult.isPasswordError,
                        passwordError = validationResult.passwordError,
                        isConfirmPasswordError = validationResult.isConfirmPasswordError,
                        confirmPasswordError = validationResult.confirmPasswordError
                    )
                }
                return@launch
            }

            // TODO Passwords need hashing with salt
            // Proceed with registration
            userRepository.registerUser(
                UserModel(
                    name = _uiState.value.name,
                    surname = _uiState.value.surname,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                )
            ).onSuccess { user ->
                _uiState.update {
                    it.transformTo<SignupUiState.Success>(user = user)
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.transformTo<SignupUiState.Error>(message = exception.message ?: "Something went wrong. Try again.")
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update {
            when (it) {
                is SignupUiState.Idle -> it.copy(
                    name = name, isNameError = false, nameError = ""
                )
                is SignupUiState.Loading -> it
                is SignupUiState.Error -> it.transformTo<SignupUiState.Idle>().copy(
                    name = name, isNameError = false, nameError = ""
                )
                is SignupUiState.Success -> it
            }
        }
    }

    fun updateSurname(surname: String) {
        _uiState.update {
            when (it) {
                is SignupUiState.Idle -> it.copy(
                    surname = surname
                )
                is SignupUiState.Loading -> it
                is SignupUiState.Error -> it.transformTo<SignupUiState.Idle>().copy(
                    surname = surname
                )
                is SignupUiState.Success -> it
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            when (it) {
                is SignupUiState.Idle -> it.copy(
                    email = email, isEmailError = false, emailError = ""
                )
                is SignupUiState.Loading -> it
                is SignupUiState.Error -> it.transformTo<SignupUiState.Idle>().copy(
                    email = email, isEmailError = false, emailError = ""
                )
                is SignupUiState.Success -> it
            }
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            when (it) {
                is SignupUiState.Idle -> it.copy(
                    password = password, isPasswordError = false, passwordError = ""
                )
                is SignupUiState.Loading -> it
                is SignupUiState.Error -> it.transformTo<SignupUiState.Idle>().copy(
                    password = password, isPasswordError = false, passwordError = ""
                )
                is SignupUiState.Success -> it
            }
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            when (it) {
                is SignupUiState.Idle -> it.copy(
                    confirmPassword = confirmPassword, isConfirmPasswordError = false, confirmPasswordError = ""
                )
                is SignupUiState.Loading -> it
                is SignupUiState.Error -> it.transformTo<SignupUiState.Idle>().copy(
                    confirmPassword = confirmPassword, isConfirmPasswordError = false, confirmPasswordError = ""
                )
                is SignupUiState.Success -> it
            }
        }
    }

    private data class ValidationResult(
        val isNameError: Boolean = false,
        val nameError: String = "",
        val isEmailError: Boolean = false,
        val emailError: String = "",
        val isPasswordError: Boolean = false,
        val passwordError: String = "",
        val isConfirmPasswordError: Boolean = false,
        val confirmPasswordError: String = ""
    )

    // TODO Increase check capacity for forbidden values, sizes
    private fun validate(): ValidationResult {
        val isNameError = _uiState.value.name.isBlank()
        val nameError = if (isNameError) "Name is required." else ""

        val isEmailError = _uiState.value.email.isBlank()
        val emailError = if (isEmailError) "Email is required." else ""

        val isPasswordError = _uiState.value.password.isBlank()
        val passwordError = if (isPasswordError) "Password is required." else ""

        val isConfirmPasswordError = _uiState.value.confirmPassword.isBlank() || _uiState.value.password != _uiState.value.confirmPassword
        val confirmPasswordError = when {
            _uiState.value.password.isBlank() -> "Password is required."
            _uiState.value.password != _uiState.value.confirmPassword -> "Passwords must match."
            else -> ""
        }

        return ValidationResult(
            isNameError = isNameError,
            nameError = nameError,
            isEmailError = isEmailError,
            emailError = emailError,
            isPasswordError = isPasswordError,
            passwordError = passwordError,
            isConfirmPasswordError = isConfirmPasswordError,
            confirmPasswordError = confirmPasswordError
        )
    }
}