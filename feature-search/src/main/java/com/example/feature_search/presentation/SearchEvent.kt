package com.example.feature_search.presentation

import com.example.core_model.enum.ConversationType

sealed interface SearchEvent {
    data class NavigateToConversationDetail(
        val conversationId: String
    ): SearchEvent
}