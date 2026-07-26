package com.example.chatapplication

sealed interface StartupState {
    data object Loading : StartupState
    data object Login : StartupState
    data object Chat : StartupState
}