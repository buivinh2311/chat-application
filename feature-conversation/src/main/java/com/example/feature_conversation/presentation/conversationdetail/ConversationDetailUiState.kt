package com.example.feature_conversation.presentation.conversationdetail

import com.example.core_model.Message

data class ConversationDetailUiState(
    val conversationDetailData: ConversationDetailData,
    val currentUserId: String,
    val isConnected: Boolean,
    val isSentRequest: Boolean,
    val messages: List<Message>
)