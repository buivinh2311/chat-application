package com.example.core_domain.repository

import com.example.core_model.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getConversation(conversationId: String): Conversation?
    fun observerConversation(conversationId: String): Flow<Conversation?>
    suspend fun ensureConversation(user1: String, user2: String): String
}