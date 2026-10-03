package com.example.feature_conversation.presentation.conversationdetail

import com.example.core_model.Conversation
import com.example.core_model.User

sealed interface ConversationDetailData {
    data class Direct(
        val conversation: Conversation,
        val user: User
    ) : ConversationDetailData

    data class Group(
        val conversation: Conversation,
        val group: com.example.core_model.Group,
        val users: Map<String, User>
    ) : ConversationDetailData
}