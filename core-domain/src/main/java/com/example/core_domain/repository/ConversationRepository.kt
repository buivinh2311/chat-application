package com.example.core_domain.repository

import com.example.core_model.Conversation
import com.example.core_model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getConversation(conversationId: String): Conversation?
    fun observerConversation(conversationId: String): Flow<Conversation?>
    fun getAllConversation(userId: String): Flow<List<Conversation>>
    suspend fun ensureConversation(user1: FirebaseUser, user2: User): String
}