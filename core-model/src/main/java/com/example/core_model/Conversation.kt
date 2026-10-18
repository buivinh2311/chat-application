package com.example.core_model

import com.example.core_model.enum.ConversationType

data class Conversation(
    val id: String,
    val type: ConversationType,
    val participants: List<String>,
    val lastMessageActorId: String?,
    val lastMessageActorName: String?,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val createdAt: String
)