package com.example.feature_conversation.presentation.conversationoption

sealed interface ConversationOptionEvent {
    data object ShowError: ConversationOptionEvent
}