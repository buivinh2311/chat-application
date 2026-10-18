package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_domain.repository.UserRepository
import com.example.feature_conversation.domain.model.FileInfo
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_utils.AppConstants.CLOUD_NAME
import com.google.firebase.Timestamp
import javax.inject.Inject

class SendFileMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        fileInfo: FileInfo
    ): Result<Unit> {
        val senderId = authRepository
            .getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val sender = userRepository
            .getUserById(senderId)
            .getOrElse { return Result.failure(it) }
            ?: return Result.failure(IllegalStateException("User not found"))

        val uploadResult = storageRepository
            .upload(fileInfo.file)
            .getOrElse { return Result.failure(it) }

        val thumbnailUrl = if (fileInfo.mimeType == "application/pdf") {
            "https://res.cloudinary.com/$CLOUD_NAME/image/upload/pg_1,w_400,h_400,c_fill/${uploadResult.publicId}"
        } else {
            null
        }

        val message = Message.File(
            id = "",
            conversationId = conversationId,
            senderId = senderId,
            fileUrl = uploadResult.url,
            thumbnailUrl = thumbnailUrl,
            fileName = fileInfo.file.name,
            fileSize = uploadResult.bytes,
            createdAt = Timestamp.now(),
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            width = uploadResult.width ?: 0,
            height = uploadResult.height ?: 0,
            mimeType = fileInfo.mimeType
        )

        return messageRepository.sendMessage(
            senderName = sender.fullName,
            message = message
        )
    }
}