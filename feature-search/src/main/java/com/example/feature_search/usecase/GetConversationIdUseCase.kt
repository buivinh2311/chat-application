package com.example.feature_search.usecase

import android.util.Log
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.UserRepository
import javax.inject.Inject

class GetConversationIdUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(uid: String): String? {
        return try {
            val user1 = authRepository.getCurrentUser()
                ?: throw IllegalStateException("User not logged in")
            val user2 = userRepository.getUser(uid)
                ?: throw IllegalStateException("User not found")
            conversationRepository.ensureConversation(user1, user2)
        } catch (_: Exception) {
            null
        }
    }
}