package com.example.feature_settings.presentation.state

data class ProfileUiState (
    val isLoading: Boolean = false,
    val isFirstNameBlank: Boolean = false,
    val isLastNameBlank: Boolean = false,
    val isUpdateSuccess: Boolean? = null
)