package com.example.feature_settings.presentation.settings

sealed interface SettingsEvent {
    data object ShowError: SettingsEvent
}