package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.Message
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_model.enum.MessageStatus
import com.google.firebase.Timestamp
import javax.inject.Inject

class ForwardMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationWithInfos: List<ConversationWithInfo>,
        message: Message,
        accompanyingText: String
    ): Result<Unit> {
        if(message is Message.System) {
            return Result.failure(IllegalStateException("Invalid message"))
        }

        val currentUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val sender = userRepository
            .getUserById(currentUserId)
            .getOrElse { return Result.failure(it) }
            ?: return Result.failure(IllegalStateException("User not found"))

        return runCatching {
            conversationWithInfos.forEach { conversationWithInfo ->
                val conversation = conversationWithInfo.conversation
                val forwardedMessage = createForwardedMessage(
                    message = message,
                    conversationId = conversation.id,
                    currentUserId = currentUserId
                )
                messageRepository.sendMessage(
                    senderName = sender.fullName,
                    message = forwardedMessage
                ).getOrThrow()
                val accompanyingMessage = Message.Text(
                    id = "",
                    conversationId = conversation.id,
                    senderId = currentUserId,
                    createdAt = Timestamp.now(),
                    status = MessageStatus.DELIVERED,
                    reactions = emptyMap(),
                    text = accompanyingText
                )
                if(accompanyingText.isNotBlank()) {
                    messageRepository.sendMessage(
                        senderName = sender.fullName,
                        message = accompanyingMessage
                    ).getOrThrow()
                }
            }
        }
    }
}

private fun createForwardedMessage(
    message: Message,
    conversationId: String,
    currentUserId: String
): Message {
    return when (message) {
        is Message.Text -> message.copy(
            id = "",
            conversationId = conversationId,
            senderId = currentUserId,
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            replyPreview = null,
            createdAt = Timestamp.now()
        )

        is Message.Image -> message.copy(
            id = "",
            conversationId = conversationId,
            senderId = currentUserId,
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            replyPreview = null,
            createdAt = Timestamp.now()
        )

        is Message.Video -> message.copy(
            id = "",
            conversationId = conversationId,
            senderId = currentUserId,
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            replyPreview = null,
            createdAt = Timestamp.now()
        )

        is Message.File -> message.copy(
            id = "",
            conversationId = conversationId,
            senderId = currentUserId,
            status = MessageStatus.DELIVERED,
            reactions = emptyMap(),
            replyPreview = null,
            createdAt = Timestamp.now()
        )

        is Message.System -> message
    }
}