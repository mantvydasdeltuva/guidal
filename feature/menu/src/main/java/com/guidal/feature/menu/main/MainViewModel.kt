package com.guidal.feature.menu.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO Extract string resources
internal class MainViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(
        value = MainUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun resetState() {
        _uiState.update {
            MainUiState.Idle()
        }
    }

    fun onNavigation() {
        _uiState.update {
            MainUiState.Idle(isNavigating = true)
        }
    }
}