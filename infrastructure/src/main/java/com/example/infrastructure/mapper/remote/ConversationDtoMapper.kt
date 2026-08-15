package com.example.infrastructure.mapper.remote

import com.example.core_model.Conversation
import com.example.core_model.enum.ConversationType
import com.example.core_network.dto.ConversationDto

fun ConversationDto.toDomain(): Conversation {
    return Conversation(
        id = id,
        type = ConversationType.valueOf(type),
        participants = participants,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime,
        createdAt = createdAt
    )
}

fun Conversation.toDto(): ConversationDto {
    return ConversationDto(
        id = id,
        type = type.name,
        participants = participants,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime,
        createdAt = createdAt
    )
}