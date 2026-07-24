package com.example.feature_auth.presentation.event

sealed interface RegisterEvent {
    data object NavigateToLogin : RegisterEvent
    data object ShowError : RegisterEvent
}