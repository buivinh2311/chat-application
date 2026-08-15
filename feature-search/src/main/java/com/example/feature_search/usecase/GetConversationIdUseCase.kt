package com.example.feature_search.usecase

import android.util.Log
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import javax.inject.Inject

class GetConversationIdUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(user2: String): String? {
        return try {
            val user1 = authRepository.getCurrentUserId()
                ?: throw IllegalStateException("User not logged in")
            Log.d("CONVERSATION_DIRECT", "success")
            conversationRepository.ensureConversation(user1, user2)
        } catch (e: Exception) {
            e.message?.let { Log.d("CONVERSATION_DIRECT", it) }
            null
        }
    }
}