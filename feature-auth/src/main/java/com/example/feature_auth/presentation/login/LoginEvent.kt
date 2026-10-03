package com.example.feature_auth.presentation.login

sealed interface LoginEvent {
    data object NavigateToHome: LoginEvent
    data object ShowBanner: LoginEvent
}