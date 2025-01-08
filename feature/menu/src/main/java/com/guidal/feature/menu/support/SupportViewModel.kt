package com.guidal.feature.menu.support

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class SupportViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<SupportUiState>(
        value = SupportUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.transformTo<SupportUiState.Idle>().copy(
            )
        }
    }

    fun resetState() {
        _uiState.update {
            it.transformTo<SupportUiState.Idle>().copy(
                isNavigating = false
            )
        }
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<SupportUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }
}