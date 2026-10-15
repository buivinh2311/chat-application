package com.example.core_network.dto

import com.example.core_model.enum.MessageType

data class ReplyPreviewDto (
    val messageId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val type: String = MessageType.TEXT.name,
    val mimeType: String? = null,
    val contentPreview: String = ""
)