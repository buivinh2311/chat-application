package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.MessageRepository
import javax.inject.Inject

class MarkMessageAsReadUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(conversationId: String, messageId: String) {
        try {
            val uid = authRepository.getCurrentUserId()
                ?: throw IllegalStateException("User not logged in")
            messageRepository.markAsRead(conversationId, messageId, uid)
        } catch (_: Exception) {

        }
    }
}