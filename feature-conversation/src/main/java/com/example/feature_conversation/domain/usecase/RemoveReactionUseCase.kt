package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import javax.inject.Inject

class RemoveReactionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        messageId: String
    ): Result<Unit> {
        val currentUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))
        return messageRepository.removeReaction(
            conversationId = conversationId,
            messageId = messageId,
            userId = currentUserId
        )
    }
}