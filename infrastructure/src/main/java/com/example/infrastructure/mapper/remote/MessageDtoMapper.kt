package com.example.infrastructure.mapper.remote

import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_model.enum.MessageType
import com.example.core_model.enum.SystemMessageType
import com.example.core_network.dto.MessageDto
import com.google.firebase.Timestamp

fun MessageDto.toDomain(): Message {
    val status = MessageStatus.valueOf(status)
    val timestamp = createdAt ?: Timestamp.now()
    val imageWidth = imageWidth ?: 0
    val imageHeight = imageHeight ?: 0
    return when (
        runCatching {
            MessageType.valueOf(type)
        }.getOrDefault(MessageType.TEXT)
    ) {
        MessageType.TEXT -> Message.Text(
            id = id,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDomain(),
            text = text.orEmpty()
        )

        MessageType.IMAGE -> Message.Image(
            id = id,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDomain(),
            fileUrl = fileUrl.orEmpty(),
            fileName = fileName.orEmpty(),
            fileSize = fileSize ?: 0,
            width = imageWidth,
            height = imageHeight,
            mimeType = mimeType.orEmpty()
        )

        MessageType.VIDEO -> Message.Video(
            id = id,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDomain(),
            fileUrl = fileUrl.orEmpty(),
            thumbnailUrl = thumbnailUrl.orEmpty(),
            fileName = fileName.orEmpty(),
            fileSize = fileSize ?: 0,
            duration = duration ?: 0,
            width = imageWidth,
            height = imageHeight,
            mimeType = mimeType.orEmpty()
        )

        MessageType.FILE -> Message.File(
            id = id,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDomain(),
            fileUrl = fileUrl.orEmpty(),
            thumbnailUrl = thumbnailUrl.orEmpty(),
            fileName = fileName.orEmpty(),
            fileSize = fileSize ?: 0,
            width = imageWidth,
            height = imageHeight,
            mimeType = mimeType.orEmpty()
        )

        MessageType.SYSTEM -> Message.System(
            id = id,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            systemMessageType = runCatching {
                SystemMessageType.valueOf(systemMessageType.orEmpty())
            }.getOrDefault(SystemMessageType.PIN_MESSAGE),
            actorId = actorId.orEmpty(),
            targetMessageId = targetMessageId
        )
    }
}

fun Message.toDto(messageId: String): MessageDto {
    val status = status.toString()
    val timestamp = createdAt
    return when (this) {
        is Message.Text -> MessageDto(
            id = messageId,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDto(),
            type = MessageType.TEXT.name,
            text = text
        )

        is Message.Image -> MessageDto(
            id = messageId,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDto(),
            type = MessageType.IMAGE.name,
            fileUrl = fileUrl,
            fileName = fileName,
            fileSize = fileSize,
            imageWidth = width,
            imageHeight = height,
            mimeType = mimeType
        )

        is Message.Video -> MessageDto(
            id = messageId,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDto(),
            type = MessageType.VIDEO.name,
            fileUrl = fileUrl,
            thumbnailUrl = thumbnailUrl,
            fileName = fileName,
            fileSize = fileSize,
            duration = duration,
            imageWidth = width,
            imageHeight = height,
            mimeType = mimeType
        )

        is Message.File -> MessageDto(
            id = messageId,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            replyPreview = replyPreview?.toDto(),
            type = MessageType.FILE.name,
            fileUrl = fileUrl,
            fileName = fileName,
            fileSize = fileSize,
            imageWidth = width,
            imageHeight = height,
            mimeType = mimeType
        )

        is Message.System -> MessageDto(
            id = messageId,
            conversationId = conversationId,
            senderId = senderId,
            createdAt = timestamp,
            status = status,
            reactions = reactions,
            type = MessageType.SYSTEM.name,
            systemMessageType = systemMessageType.name,
            actorId = actorId,
            targetMessageId = targetMessageId
        )
    }
}

fun MessageDto.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "conversationId" to conversationId,
        "senderId" to senderId,
        "type" to type,
        "text" to text,
        "imageWidth" to imageWidth,
        "imageHeight" to imageHeight,
        "thumbnailUrl" to thumbnailUrl,
        "duration" to duration,
        "fileUrl" to fileUrl,
        "fileName" to fileName,
        "fileSize" to fileSize,
        "mimeType" to mimeType,
        "status" to status,
        "reactions" to reactions,
        "replyPreview" to replyPreview,
        "createdAt" to createdAt,
        "readBy" to readBy
    ).filterValues { it != null }
        .mapValues { (_, value) -> value!! }
}