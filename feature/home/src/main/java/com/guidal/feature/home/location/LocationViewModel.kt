package com.guidal.feature.home.location

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// LocationViewModel.kt
@HiltViewModel
internal class LocationViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationUiState>(
        value = LocationUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun resetState() {
        _uiState.update {
            LocationUiState.Idle()
        }
    }
}