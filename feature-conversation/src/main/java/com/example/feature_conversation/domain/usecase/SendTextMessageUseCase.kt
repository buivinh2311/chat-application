package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp
import javax.inject.Inject

class SendTextMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        text: String
    ): Result<Unit> {
        val textTrimmed = text.trim()
        require(textTrimmed.isNotEmpty()) { "Message cannot be empty" }

        val senderId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val message = Message.Text(
            id = "",
            conversationId = conversationId,
            senderId = senderId,
            createdAt = Timestamp.now(),
            status = MessageStatus.DELIVERED,
            text = text
        )

        return messageRepository.sendMessage(message)
    }
}