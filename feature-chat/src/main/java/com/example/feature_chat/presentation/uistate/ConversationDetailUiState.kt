package com.example.feature_chat.presentation.uistate

import com.example.core_model.Message

data class ConversationDetailUiState(
    val conversationDetailData: ConversationDetailData,
    val currentUserId: String,
    val isConnected: Boolean,
    val isSentRequest: Boolean,
    val messages: List<Message>
)