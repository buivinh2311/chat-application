package com.example.core_domain.repository

import android.net.Uri
import com.example.core_model.Message
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(
        conversationId: String,
        pageSize: Int = 30
    ): Flow<List<Message>>

    suspend fun loadMoreMessages(
        conversationId: String,
        lastVisibleTimestamp: Timestamp,
        pageSize: Int = 30
    ): List<Message>

    suspend fun sendMessage(message: Message)

    suspend fun markAsRead(
        conversationId: String,
        messageId: String, uid: String
    )
}