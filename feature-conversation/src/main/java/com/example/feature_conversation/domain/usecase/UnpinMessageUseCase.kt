package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_model.enum.SystemMessageType
import com.google.firebase.Timestamp
import javax.inject.Inject

class UnpinMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        messageId: String
    ): Result<Unit> {
        val currentUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val sender = userRepository
            .getUserById(currentUserId)
            .getOrElse { return Result.failure(it) }
            ?: return Result.failure(IllegalStateException("User not found"))

        return runCatching {
            messageRepository.unpinMessage(
                conversationId = conversationId,
                messageId = messageId
            ).getOrThrow()

            val systemMessage = Message.System(
                id = "",
                conversationId = conversationId,
                senderId = sender.id,
                status = MessageStatus.DELIVERED,
                createdAt = Timestamp.now(),
                systemMessageType = SystemMessageType.UNPIN_MESSAGE,
                targetMessageId = messageId
            )

            messageRepository.sendMessage(
                senderName = sender.fullName,
                message = systemMessage
            ).getOrThrow()
        }
    }
}