package com.example.feature_chat.usecase

import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(conversationId: String): Flow<List<Message>> {
        return messageRepository.getMessages(conversationId).catch{ emit(emptyList()) }
    }
}