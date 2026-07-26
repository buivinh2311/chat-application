package com.example.feature_auth.presentation.event

sealed interface LoginEvent {
    data object navigateToHome: LoginEvent
    data object showBanner: LoginEvent
}