package com.example.feature_profile.presentation.profile

sealed interface ProfileEvent {
    data object ShowError: ProfileEvent
    data object UpdateSuccessful: ProfileEvent
}