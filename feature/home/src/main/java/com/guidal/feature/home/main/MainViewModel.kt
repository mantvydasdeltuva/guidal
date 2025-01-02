package com.guidal.feature.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidal.data.db.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract string resources
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(
        value = MainUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().onSuccess { categories ->
                _uiState.update {
                    it.transformTo<MainUiState.Idle>().copy(
                        categories = categories.map { category ->
                            category.copy()
                        }
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.transformTo<MainUiState.Error>().copy(
                        message = exception.message ?: "Unexpected error. Try again"
                    )
                }
            }
        }
    }

    fun updateSearchState() {
        _uiState.update {
            it.transformTo<MainUiState.Idle>().copy(
                isSearchEnabled = !it.isSearchEnabled
            )
        }
    }
}