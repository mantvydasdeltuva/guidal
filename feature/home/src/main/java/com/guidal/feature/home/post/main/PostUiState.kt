package com.guidal.feature.home.post.main

// PostUiState.kt
internal sealed interface PostUiState {
    val isLoading: Boolean

    data class Idle(
        override val isLoading: Boolean = false
    ) : PostUiState

    data class Loading(
        override val isLoading: Boolean = true
    ) : PostUiState

    data class Error(
        val message: String,
        override val isLoading: Boolean = false
    ) : PostUiState

    data class Success(
        override val isLoading: Boolean = false
    ) : PostUiState
}

internal inline fun <reified T : PostUiState> PostUiState.transformTo(
    message: String? = null,
) : T {
    return when (T::class) {
        PostUiState.Idle::class -> PostUiState.Idle(
            isLoading = this.isLoading
        ) as T
        PostUiState.Loading::class -> PostUiState.Loading(
            isLoading = this.isLoading
        ) as T
        PostUiState.Error::class -> {
            if (this is PostUiState.Error) {
                PostUiState.Error(
                    message = message ?: this.message,
                    isLoading = this.isLoading
                ) as T
            } else {
                requireNotNull(message) { "`message` required for state type ${T::class}" }
                PostUiState.Error(
                    message = message,
                    isLoading = this.isLoading
                ) as T
            }
        }
        PostUiState.Success::class -> PostUiState.Success(
            isLoading = this.isLoading
        ) as T
        else -> throw IllegalArgumentException("Unsupported state type: ${T::class}")
    }
}