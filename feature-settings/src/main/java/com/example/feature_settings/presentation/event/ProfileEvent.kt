package com.example.feature_settings.presentation.event

sealed interface ProfileEvent {
    data object ShowBanner: ProfileEvent
}