package com.example.feature_settings.presentation.state

import com.example.core_model.User

data class ProfileUiState (
    val currentUser: User,
    val isFirstNameBlank: Boolean = false,
    val isLastNameBlank: Boolean = false
)