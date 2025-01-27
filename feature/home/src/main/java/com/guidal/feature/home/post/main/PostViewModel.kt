package com.guidal.feature.home.post.main

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
internal class PostViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<PostUiState>(
        value = PostUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    init {
        simulateLoading()
    }

    private fun simulateLoading() {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<PostUiState.Loading>()
            }

            delay(Random.nextLong(1000, 2001))

            _uiState.update {
                it.transformTo<PostUiState.Idle>()
            }
        }
    }

    fun resetState() {
        _uiState.update {
            PostUiState.Idle()
        }
    }
}