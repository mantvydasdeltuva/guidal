package com.guidal.feature.home.location.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.data.db.models.LocationModel
import com.guidal.data.db.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LocationViewViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationViewUiState>(
        value = LocationViewUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun fetch(locationId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<LocationViewUiState.Loading>()
            }

            var locationResult: Result<LocationModel>? = null

            val locationJob = launch {
                locationRepository.getLocationById(locationId)
                    .onSuccess { location ->
                        locationResult = Result.success(location)
                    }
                    .onFailure { exception ->
                        locationResult = Result.failure(exception)
                    }
            }

            // Wait for tasks to complete
            locationJob.join()

            // Update UI state
            _uiState.update {
                when {
                    locationResult?.isSuccess == true -> {
                        it.transformTo<LocationViewUiState.Idle>().copy(
                            location = locationResult?.getOrNull()?.copy(),
                        )
                    }
                    else -> {
                        it.transformTo<LocationViewUiState.Error>(
                            message = "Failed to load location."
                        ).copy(
                            location = null
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update {
            LocationViewUiState.Idle()
        }
    }
}