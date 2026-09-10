package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp
import java.util.UUID
import javax.inject.Inject

class SendTextMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        text: String
    ): Result<Unit> {
        return try {
            val textTrimmed = text.trim()
            require(textTrimmed.isNotEmpty()) { "Message cannot be empty" }
            val senderId = authRepository.getCurrentUserId()
                ?: return Result.failure(IllegalStateException("User not logged in"))
            val message = Message.Text(
                id = UUID.randomUUID().toString(),
                conversationId = conversationId,
                senderId = senderId,
                createdAt = Timestamp.now(),
                status = MessageStatus.DELIVERED,
                text = text
            )
            messageRepository.sendMessage(message)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}