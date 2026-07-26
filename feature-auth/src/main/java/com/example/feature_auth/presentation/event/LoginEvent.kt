package com.example.feature_auth.presentation.event

sealed interface LoginEvent {
    data object NavigateToHome: LoginEvent
    data object ShowBanner: LoginEvent
}