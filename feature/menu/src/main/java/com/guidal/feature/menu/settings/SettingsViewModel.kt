package com.guidal.feature.menu.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsUiState>(
        value = SettingsUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.transformTo<SettingsUiState.Idle>().copy(
            )
        }
    }

    fun resetState() {
        _uiState.update {
            it.transformTo<SettingsUiState.Idle>().copy(
                isNavigating = false
            )
        }
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<SettingsUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }
}