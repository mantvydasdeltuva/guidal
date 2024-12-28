package com.guidal.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.data.db.repositories.UserRepository
import com.guidal.data.utils.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract string resources
@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreUtils: DataStoreUtils
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(
        value = LoginUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<LoginUiState.Loading>()
            }

            // TODO Remove
            delay(2000)

            // User input validation
            val validationResult = validate()
            val errors = listOf(
                validationResult.isEmailError,
                validationResult.isPasswordError
            )
            if (errors.any { it }) {
                _uiState.update {
                    it.transformTo<LoginUiState.Idle>().copy(
                        isEmailError = validationResult.isEmailError,
                        emailError = validationResult.emailError,
                        isPasswordError = validationResult.isPasswordError,
                        passwordError = validationResult.passwordError
                    )
                }
                return@launch
            }

            // Proceed with authentication
            userRepository.authenticateUser(
                email = _uiState.value.email,
                password = _uiState.value.password
            ).onSuccess { user ->
                _uiState.update {
                    it.transformTo<LoginUiState.Success>(user = user).also { state ->
                        dataStoreUtils.saveUser(state.user)
                    }
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.transformTo<LoginUiState.Error>(message = exception.message ?: "Something went wrong. Try again.")
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            when (it) {
                is LoginUiState.Idle -> it.copy(
                    email = email, isEmailError = false, emailError = ""
                )
                is LoginUiState.Loading -> it
                is LoginUiState.Error -> it.transformTo<LoginUiState.Idle>().copy(
                    email = email, isEmailError = false, emailError = ""
                )
                is LoginUiState.Success -> it
            }
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            when (it) {
                is LoginUiState.Idle -> it.copy(
                    password = password, isPasswordError = false, passwordError = ""
                )
                is LoginUiState.Loading -> it
                is LoginUiState.Error -> it.transformTo<LoginUiState.Idle>().copy(
                    password = password, isPasswordError = false, passwordError = ""
                )
                is LoginUiState.Success -> it
            }
        }
    }

    private data class ValidationResult(
        val isEmailError: Boolean = false,
        val emailError: String = "",
        val isPasswordError: Boolean = false,
        val passwordError: String = ""
    )

    private fun validate(): ValidationResult {
        val isEmailError = _uiState.value.email.isBlank()
        val emailError = if (isEmailError) "Email is required." else ""

        val isPasswordError = _uiState.value.password.isBlank()
        val passwordError = if (isPasswordError) "Password is required." else ""

        return ValidationResult(
            isEmailError = isEmailError,
            emailError = emailError,
            isPasswordError = isPasswordError,
            passwordError = passwordError
        )
    }
}