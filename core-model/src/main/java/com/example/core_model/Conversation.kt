package com.example.core_model

import com.example.core_model.enum.ConversationType
import com.example.core_model.enum.MessageType

data class Conversation(
    val id: String,
    val type: ConversationType,
    val participants: List<String>,
    val lastMessageActorId: String?,
    val lastMessageActorName: String?,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val systemMessage: Boolean,
    val createdAt: String
)