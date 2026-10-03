package com.example.feature_settings.presentation.settings

import com.example.core_model.User
import com.example.core_model.enum.DarkModeState

data class SettingsUiState (
    val currentUser: User,
    val darkModeState: DarkModeState
)