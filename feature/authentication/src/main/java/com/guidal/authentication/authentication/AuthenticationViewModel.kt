package com.guidal.authentication.authentication

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
internal class AuthenticationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreUtils: DataStoreUtils
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthenticationUiState>(
        value = AuthenticationUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun resetState() {
        _uiState.update {
            AuthenticationUiState.Idle()
        }
    }

    fun onNavigation() {
        _uiState.update {
            AuthenticationUiState.Idle(isNavigating = true)
        }
    }

    fun guest() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<AuthenticationUiState.Loading>()
            }

            // TODO Remove
            delay(1000)

            // Proceed with guest account
            userRepository.authenticateGuest().onSuccess { user ->
                _uiState.update {
                    it.transformTo<AuthenticationUiState.Success>(user = user).also { state ->
                        dataStoreUtils.saveUser(state.user)
                    }
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.transformTo<AuthenticationUiState.Error>(message = exception.message ?: "Something went wrong. Try again.")
                }
            }
        }
    }

    fun google() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<AuthenticationUiState.Loading>()
            }

            // TODO Remove
            delay(1000)

            // TODO Proceed with google account

            _uiState.update {
                it.transformTo<AuthenticationUiState.Error>(message = "This feature is currently not supported.")
            }
        }
    }
}