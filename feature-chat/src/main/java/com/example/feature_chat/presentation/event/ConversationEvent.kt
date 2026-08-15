package com.example.feature_chat.presentation.event

sealed interface ConversationEvent {
    data object ShowError: ConversationEvent
}