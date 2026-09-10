package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import com.google.firebase.Timestamp
import javax.inject.Inject

class LoadMoreMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        lastVisibleTimestamp: Timestamp
    ): List<Message> {
        return try {
            messageRepository.loadMoreMessages(conversationId, lastVisibleTimestamp)
        } catch (_: Exception) {
            emptyList()
        }
    }
}