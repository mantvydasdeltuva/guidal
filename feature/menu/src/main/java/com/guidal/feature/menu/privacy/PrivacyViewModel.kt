package com.guidal.feature.menu.privacy

import android.Manifest
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PrivacyViewModel @Inject constructor(
    private val context: Application,
) : ViewModel() {
    private val _uiState = MutableStateFlow<PrivacyUiState>(
        value = PrivacyUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<PrivacyUiState.Idle>().copy(
                    isLocationEnabled = isLocationPermissionGranted(),
                    // TODO Implement data collection
                    isCollectionEnabled = false
                )
            }
        }
    }

    fun updateLocationState(enabled: Boolean) {
        _uiState.update {
            it.transformTo<PrivacyUiState.Idle>().copy(
                isLocationEnabled = enabled
            )
        }
    }

    // TODO Implement data collection
    fun updateCollectionState(enabled: Boolean) {
        _uiState.update {
            it.transformTo<PrivacyUiState.Idle>().copy(
                isCollectionEnabled = enabled
            )
        }
    }

    fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<PrivacyUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        val fineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val coarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        return fineLocation || coarseLocation
    }
}