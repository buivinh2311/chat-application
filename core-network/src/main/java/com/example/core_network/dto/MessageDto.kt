package com.example.core_network.dto

import com.example.core_model.enum.MessageStatus
import com.example.core_model.enum.MessageType
import com.google.firebase.Timestamp

data class MessageDto(
    val id: String = "",
    val conversationId: String = "",
    val senderId: String = "",
    val type: String = MessageType.TEXT.name,
    val text: String? = null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val thumbnailUrl: String? = null,
    val duration: Long? = null,
    val fileUrl: String? = null,
    val fileName: String? = null,
    val fileSize: Long? = null,
    val mimeType: String? = null,
    val status: String = MessageStatus.SENT.name,
    val createdAt: Timestamp? = null,
    val readBy: List<String> = emptyList()
)