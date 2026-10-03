package com.example.feature_conversation.presentation.conversation

sealed interface ConversationEvent {
    data object ShowError: ConversationEvent
}