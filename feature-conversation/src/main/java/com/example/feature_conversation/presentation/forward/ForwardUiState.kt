package com.example.feature_conversation.presentation.forward

import com.example.core_model.Message
import com.example.core_model.composite.ConversationWithInfo

data class ForwardUiState(
    val message: Message,
    val conversationWithInfos: List<ConversationWithInfo>,
    val conversationWithInfoSelected: List<ConversationWithInfo>
)