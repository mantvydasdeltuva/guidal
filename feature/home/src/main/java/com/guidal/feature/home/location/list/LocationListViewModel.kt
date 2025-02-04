package com.guidal.feature.home.location.list

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

// LocationListViewModel.kt
@HiltViewModel
internal class LocationListViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationListUiState>(
        value = LocationListUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun fetch(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<LocationListUiState.Loading>()
            }

            var locationsResult: Result<List<LocationModel>>? = null

            val locationJob = launch {
                locationRepository.getLocationsByCategory(categoryId)
                    .onSuccess { locations ->
                        locationsResult = Result.success(locations)
                    }
                    .onFailure { exception ->
                        locationsResult = Result.failure(exception)
                    }
            }

            // Wait for tasks to complete
            locationJob.join()

            // Update UI state
            _uiState.update {
                when {
                    locationsResult?.isSuccess == true -> {
                        it.transformTo<LocationListUiState.Idle>().copy(
                            locations = locationsResult?.getOrNull()?.map { location ->
                                location.copy()
                            } ?: emptyList()
                        )
                    }
                    else -> {
                        it.transformTo<LocationListUiState.Error>(
                            message = "Failed to load locations."
                        ).copy(
                            locations = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update {
            LocationListUiState.Idle()
        }
    }
}