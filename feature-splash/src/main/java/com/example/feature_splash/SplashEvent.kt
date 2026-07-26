package com.example.feature_splash

sealed interface SplashEvent {
    data object NavigateToChat: SplashEvent
    data object NavigateToLogin: SplashEvent
}