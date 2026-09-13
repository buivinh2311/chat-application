package com.example.feature_settings.presentation.event

sealed interface ProfileEvent {
    data object ShowError: ProfileEvent
    data object UpdateSuccessful: ProfileEvent
}