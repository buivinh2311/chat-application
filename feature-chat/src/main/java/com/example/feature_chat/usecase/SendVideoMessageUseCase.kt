package com.example.feature_chat.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp
import java.io.File
import java.util.UUID
import javax.inject.Inject

class SendVideoMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        file: File
    ): Result<Unit> {
        return try {
            val senderId = authRepository.getCurrentUserId()
                ?: return Result.failure(IllegalStateException("User not logged in"))

            val uploadResult = storageRepository.upload(file, "video")
                ?: return Result.failure(Exception("Failed to upload file"))

            val thumbnailUrl = uploadResult.url
                .replace("/video/upload/", "/video/upload/so_0,w_400,h_400,c_fill/")
                .replaceAfterLast(".", "jpg")

            val message = Message.Video(
                id = UUID.randomUUID().toString(),
                conversationId = conversationId,
                senderId = senderId,
                createdAt = Timestamp.now(),
                status = MessageStatus.DELIVERED,
                fileUrl = uploadResult.url,
                thumbnailUrl = thumbnailUrl,
                fileName = file.name,
                fileSize = uploadResult.bytes,
                duration = uploadResult.duration ?: 0,
                width = uploadResult.width ?: 0,
                height = uploadResult.height ?: 0,
                mimeType = uploadResult.format.orEmpty()
            )
            messageRepository.sendMessage(message)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}