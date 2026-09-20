package com.example.feature_chat.presentation.event

sealed interface ConversationOptionEvent {
    data object ShowError: ConversationOptionEvent
}