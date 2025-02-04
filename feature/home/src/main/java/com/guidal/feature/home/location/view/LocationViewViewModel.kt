package com.guidal.feature.home.location.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
internal class LocationViewViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationViewUiState>(
        value = LocationViewUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    init {
        simulateLoading()
    }

    private fun simulateLoading() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<LocationViewUiState.Loading>()
            }

            delay(Random.nextLong(1000, 2001))

            _uiState.update {
                it.transformTo<LocationViewUiState.Idle>()
            }
        }
    }

    fun resetState() {
        _uiState.update {
            LocationViewUiState.Idle()
        }
    }
}