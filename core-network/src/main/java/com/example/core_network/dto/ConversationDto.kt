package com.example.core_network.dto

import com.example.core_model.enum.ConversationType
import com.google.firebase.Timestamp

data class ConversationDto(
    val id: String = "",
    val type: String = "",
    val displayName: String = "",
    val memberAvatars: List<String> = emptyList(),
    val participants: List<String> = emptyList(),
    val lastMessage: String? = null,
    val lastMessageTime: Timestamp? = null,
    val createdAt: Timestamp = Timestamp.now()
)