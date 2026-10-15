package com.example.infrastructure.mapper.remote

import com.example.core_model.ReplyPreview
import com.example.core_model.enum.MessageType
import com.example.core_network.dto.ReplyPreviewDto

fun ReplyPreviewDto.toDomain(): ReplyPreview {
    return ReplyPreview(
        messageId = messageId,
        senderId = senderId,
        senderName = senderName,
        type = MessageType.valueOf(type),
        mimeType = mimeType,
        contentPreview = contentPreview
    )
}

fun ReplyPreview.toDto(): ReplyPreviewDto {
    return ReplyPreviewDto(
        messageId = messageId,
        senderId = senderId,
        senderName = senderName,
        type = type.name,
        mimeType = mimeType,
        contentPreview = contentPreview
    )
}