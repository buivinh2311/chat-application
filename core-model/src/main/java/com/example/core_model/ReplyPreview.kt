package com.example.core_model

import com.example.core_model.enum.MessageType

data class ReplyPreview(
    val messageId: String,
    val senderId: String,
    val senderName: String,
    val type: MessageType,
    val mimeType: String? = null,
    val contentPreview: String
)