package com.example.feature_conversation.presentation.creategroup

sealed interface CreateGroupEvent {
    data object ShowError: CreateGroupEvent
    data object NotEnough: CreateGroupEvent
    data class NavigateToConversationDetail(
        val conversationId: String
    ): CreateGroupEvent
}