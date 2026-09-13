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
    suspend operator fun invoke(uid: String): Result<String> {
        val user1 = authRepository.getCurrentUser()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val user2 = userRepository
            .getUserById(uid)
            .getOrElse { return Result.failure(it) }
            ?: throw IllegalStateException("User not found")

        return conversationRepository.ensureConversation(user1, user2)
    }
}