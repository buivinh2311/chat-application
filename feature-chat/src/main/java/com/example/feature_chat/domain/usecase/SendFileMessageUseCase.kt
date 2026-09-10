package com.example.feature_chat.domain.usecase

import android.util.Log
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.StorageRepository
import com.example.feature_chat.domain.model.FileInfo
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_utils.AppConstants.CLOUD_NAME
import com.google.firebase.Timestamp
import java.util.UUID
import javax.inject.Inject

class SendFileMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        fileInfo: FileInfo
    ): Result<Unit> {
        return try {
            val senderId = authRepository.getCurrentUserId()
                ?: return Result.failure(IllegalStateException("User not logged in"))

            val uploadResult = storageRepository.upload(fileInfo.file)
                ?: return Result.failure(Exception("Failed to upload file"))

            val thumbnailUrl = if (fileInfo.mimeType == "application/pdf") {
                "https://res.cloudinary.com/$CLOUD_NAME/image/upload/pg_1,w_400,h_400,c_fill/${uploadResult.publicId}"
            } else {
                null
            }

            val message = Message.File(
                id = UUID.randomUUID().toString(),
                conversationId = conversationId,
                senderId = senderId,
                fileUrl = uploadResult.url,
                thumbnailUrl = thumbnailUrl,
                fileName = fileInfo.file.name,
                fileSize = uploadResult.bytes,
                createdAt = Timestamp.now(),
                status = MessageStatus.DELIVERED,
                width = uploadResult.width ?: 0,
                height = uploadResult.height ?: 0,
                mimeType = fileInfo.mimeType
            )
            messageRepository.sendMessage(message)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}