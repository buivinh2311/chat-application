package com.example.feature_everybody.presentation

sealed interface EverybodyEvent {
    data object ShowError: EverybodyEvent
}