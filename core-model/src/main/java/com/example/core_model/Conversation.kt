package com.example.core_model

import com.example.core_model.enum.ConversationType
import com.google.firebase.Timestamp

data class Conversation(
    val id: String,
    val type: ConversationType,
    val participants: List<String>,
    val lastMessage: String?,
    val lastMessageTime: Timestamp?,
    val createdAt: Timestamp
)