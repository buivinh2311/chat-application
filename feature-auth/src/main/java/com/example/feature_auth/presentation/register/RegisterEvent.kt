package com.example.feature_auth.presentation.register

sealed interface RegisterEvent {
    data object NavigateToLogin : RegisterEvent
    data object ShowError : RegisterEvent
}