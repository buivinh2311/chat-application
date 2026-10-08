package com.example.core_model

import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp

sealed class Message {
    abstract val id: String
    abstract val conversationId: String
    abstract val senderId: String
    abstract val createdAt: Timestamp
    abstract val status: MessageStatus
    abstract val reactions: Map<String, String>

    data class Text(
        override val id: String,
        override val conversationId: String,
        override val senderId: String,
        override val createdAt: Timestamp,
        override val status: MessageStatus,
        override val reactions: Map<String, String>,
        val text: String
    ) : Message()

    data class Image(
        override val id: String,
        override val conversationId: String,
        override val senderId: String,
        override val createdAt: Timestamp,
        override val status: MessageStatus,
        override val reactions: Map<String, String>,
        val fileUrl: String,
        val fileName: String,
        val fileSize: Long,
        val width: Int,
        val height: Int,
        val mimeType: String
    ) : Message()

    data class Video(
        override val id: String,
        override val conversationId: String,
        override val senderId: String,
        override val createdAt: Timestamp,
        override val status: MessageStatus,
        override val reactions: Map<String, String>,
        val fileUrl: String,
        val thumbnailUrl: String,
        val fileName: String,
        val fileSize: Long,
        val duration: Long,
        val width: Int,
        val height: Int,
        val mimeType: String
    ) : Message()

    data class File(
        override val id: String,
        override val conversationId: String,
        override val senderId: String,
        override val createdAt: Timestamp,
        override val status: MessageStatus,
        override val reactions: Map<String, String>,
        val fileUrl: String,
        val thumbnailUrl: String?,
        val fileName: String,
        val fileSize: Long,
        val width: Int,
        val height: Int,
        val mimeType: String
    ) : Message()
}