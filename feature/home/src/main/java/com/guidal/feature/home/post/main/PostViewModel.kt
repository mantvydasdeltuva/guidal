package com.guidal.feature.home.post.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class PostViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow<PostUiState>(
        value = PostUiState.Idle()
    )

    val uiState = _uiState.asStateFlow()

    fun resetState() {
        _uiState.update {
            PostUiState.Idle()
        }
    }
}