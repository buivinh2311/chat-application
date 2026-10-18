package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import javax.inject.Inject

class GetMessageByIdUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        messageId: String
    ): Result<Message> {
        return messageRepository
            .getMessageById(conversationId, messageId)
            .fold(
                onSuccess = { message ->
                    if (message != null) {
                        Result.success(message)
                    } else {
                        Result.failure(
                            IllegalStateException("Message not found")
                        )
                    }
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
    }
}