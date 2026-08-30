package com.example.feature_chat.usecase

import android.net.Uri
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.MediaFile
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_model.enum.MessageType
import com.google.firebase.Timestamp
import java.io.File
import java.util.UUID
import javax.inject.Inject

class SendImageMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        file: File
    ): Result<Unit> {
        return try {
            val senderId = authRepository.getCurrentUserId()
                ?: return Result.failure(IllegalStateException("User not logged in"))

            val uploadResult = storageRepository.upload(file, "image")
                ?: return Result.failure(Exception("Failed to upload file"))

            val message = Message.Image(
                id = UUID.randomUUID().toString(),
                conversationId = conversationId,
                senderId = senderId,
                fileUrl = uploadResult.url,
                fileName = file.name,
                fileSize = uploadResult.bytes,
                width = uploadResult.width ?: 0,
                height = uploadResult.height ?: 0,
                createdAt = Timestamp.now(),
                status = MessageStatus.DELIVERED,
                mimeType = uploadResult.format.orEmpty()
            )
            messageRepository.sendMessage(message)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}