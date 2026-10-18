package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_domain.repository.UserRepository
import com.example.feature_conversation.domain.model.FileInfo
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp
import javax.inject.Inject

class SendVideoMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        fileInfo: FileInfo
    ): Result<Unit> {
        val senderId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val sender = userRepository
            .getUserById(senderId)
            .getOrElse { return Result.failure(it) }
            ?: return Result.failure(IllegalStateException("User not found"))

        val uploadResult = storageRepository
            .upload(fileInfo.file)
            .getOrElse { return Result.failure(it) }

        val thumbnailUrl = uploadResult.url
            .replace("/video/upload/", "/video/upload/so_0,w_400/")
            .replaceAfterLast(".", "jpg")

        val message = Message.Video(
            id = "",
            conversationId = conversationId,
            senderId = senderId,
            createdAt = Timestamp.now(),
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            fileUrl = uploadResult.url,
            thumbnailUrl = thumbnailUrl,
            fileName = fileInfo.file.name,
            fileSize = uploadResult.bytes,
            duration = uploadResult.duration ?: 0,
            width = uploadResult.width ?: 0,
            height = uploadResult.height ?: 0,
            mimeType = fileInfo.mimeType
        )

        return messageRepository.sendMessage(sender.fullName, message)
    }
}