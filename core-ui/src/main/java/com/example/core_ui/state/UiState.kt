package com.example.core_ui.state

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Empty : UiState<Nothing>
    data class Success<T>(
        val data: T
    ) : UiState<T>
}