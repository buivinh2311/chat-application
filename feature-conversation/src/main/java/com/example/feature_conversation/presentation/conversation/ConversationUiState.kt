package com.example.feature_conversation.presentation.conversation

import com.example.core_model.composite.ConversationWithInfo

data class ConversationUiState(
    val currentUserId: String,
    val conversationWithInfos: List<ConversationWithInfo>
)