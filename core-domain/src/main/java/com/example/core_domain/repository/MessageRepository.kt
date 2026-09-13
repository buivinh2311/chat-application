package com.example.core_domain.repository

import android.net.Uri
import com.example.core_model.Message
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun observeMessagesByConversationId(
        conversationId: String,
        pageSize: Int = 30
    ): Flow<List<Message>>

    suspend fun loadMoreMessagesByConversationId(
        conversationId: String,
        lastVisibleTimestamp: Timestamp,
        pageSize: Int = 30
    ): Result<List<Message>>

    suspend fun sendMessage(message: Message): Result<Unit>

    suspend fun markAsRead(
        conversationId: String,
        messageId: String, uid: String
    ): Result<Unit>
}