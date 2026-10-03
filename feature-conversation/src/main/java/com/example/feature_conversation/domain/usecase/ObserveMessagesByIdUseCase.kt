package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessagesByIdUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(conversationId: String): Flow<List<Message>> {
        return messageRepository.observeMessagesByConversationId(conversationId)
    }
}