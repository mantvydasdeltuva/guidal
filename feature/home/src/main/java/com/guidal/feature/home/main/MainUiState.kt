package com.guidal.feature.home.main

import com.guidal.data.db.models.CategoryModel

internal sealed interface MainUiState {
    val categories: List<CategoryModel>
    val isSearchEnabled: Boolean

    data class Idle(
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false
    ) : MainUiState

    data class Loading(
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false
    ) : MainUiState

    data class Error(
        val message: String,
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false
    ) : MainUiState

    data class Success(
        override val categories: List<CategoryModel> = emptyList(),
        override val isSearchEnabled: Boolean = false
    ) : MainUiState
}

// TODO Extract string resources
internal inline fun <reified T : MainUiState> MainUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        MainUiState.Idle::class -> MainUiState.Idle(
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled
        ) as T
        MainUiState.Loading::class -> MainUiState.Loading(
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled
        ) as T
        MainUiState.Error::class -> {
            if (this is MainUiState.Error) {
                MainUiState.Error(
                    message = message ?: this.message,
                    categories = this.categories.toList(),
                    isSearchEnabled = this.isSearchEnabled
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                MainUiState.Error(
                    message = message,
                    categories = this.categories.toList(),
                    isSearchEnabled = this.isSearchEnabled
                ) as T
            }
        }
        MainUiState.Success::class -> MainUiState.Success(
            categories = this.categories.toList(),
            isSearchEnabled = this.isSearchEnabled
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}