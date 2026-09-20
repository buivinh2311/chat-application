package com.example.feature_chat.presentation.event

sealed interface PickGroupMemberEvent {
    data object ShowError: PickGroupMemberEvent
    data object NotEnoughMember: PickGroupMemberEvent
    data class NavigateToConversationDetail(
        val conversationId: String
    ): PickGroupMemberEvent
}