package com.example.feature_conversation.presentation.forward

interface ForwardEvent {
    data object ShowError: ForwardEvent
    data object ForwardSuccess: ForwardEvent
}