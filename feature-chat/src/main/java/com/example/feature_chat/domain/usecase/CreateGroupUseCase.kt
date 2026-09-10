package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_model.User
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val groupRepository: GroupRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        user2: User
    ): Result<Unit> {
        return try {
            val currentUser = authRepository.getCurrentUser()
                ?: throw IllegalStateException("User not logged in")
            val conversationId = conversationRepository.createConversation(currentUser, user2)
            groupRepository.createGroup(name, conversationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}