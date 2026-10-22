package com.example.infrastructure.mapper.remote

import com.example.core_model.Conversation
import com.example.core_model.Group
import com.example.core_model.User
import com.example.core_model.composite.ConversationDisplayInfo
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_model.enum.ConversationType
import com.example.core_network.dto.ConversationDto
import com.example.core_utils.toRelativeString

fun ConversationDto.toDomain(): Conversation {
    return Conversation(
        id = id,
        type = runCatching {
            ConversationType.valueOf(type)
        }.getOrDefault(ConversationType.DIRECT),
        participants = participants,
        lastMessageActorId = lastMessageActorId,
        lastMessageActorName = lastMessageActorName,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime?.toRelativeString(),
        systemMessage = systemMessage,
        createdAt = createdAt.toRelativeString()
    )
}

fun Conversation.toConversationWithUser(user: User): ConversationWithInfo {
    return ConversationWithInfo(
        conversation = this,
        displayInfo = ConversationDisplayInfo.Direct(user)
    )
}

fun Conversation.toConversationWithGroup(group: Group): ConversationWithInfo {
    return ConversationWithInfo(
        conversation = this,
        displayInfo = ConversationDisplayInfo.Group(group)
    )
}