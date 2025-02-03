package com.guidal.feature.home.location.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// LocationListViewModel.kt
@HiltViewModel
internal class LocationListViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationListUiState>(
        value = LocationListUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun resetState() {
        _uiState.update {
            LocationListUiState.Idle()
        }
    }
}