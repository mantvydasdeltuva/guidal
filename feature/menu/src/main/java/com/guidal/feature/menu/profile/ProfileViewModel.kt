package com.guidal.feature.menu.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.data.utils.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract string resources
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val dataStoreUtils: DataStoreUtils,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(
        value = ProfileUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreUtils.getUser().collect { user ->
                user?.let {
                    _uiState.update {
                        it.transformTo<ProfileUiState.Idle>(
                            user = user
                        )
                    }
                } ?: run {
                    _uiState.update {
                        it.transformTo<ProfileUiState.Error>(
                            message = "Something went wrong. Try again."
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update {
            it.transformTo<ProfileUiState.Idle>().copy(
                isNavigating = false
            )
        }
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<ProfileUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }
}