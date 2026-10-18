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

class SendImageMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
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

        val message = Message.Image(
            id = "",
            conversationId = conversationId,
            senderId = senderId,
            fileUrl = uploadResult.url,
            fileName = fileInfo.file.name,
            fileSize = uploadResult.bytes,
            width = uploadResult.width ?: 0,
            height = uploadResult.height ?: 0,
            createdAt = Timestamp.now(),
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            mimeType = fileInfo.mimeType
        )

        return messageRepository.sendMessage(sender.fullName, message)
    }
}