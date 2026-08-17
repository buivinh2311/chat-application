package com.example.feature_chat.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.enum.ConversationType
import com.example.feature_chat.presentation.screen.ConversationDetailData
import javax.inject.Inject

class GetConversationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): ConversationDetailData? {
        return try {
            val conversation = conversationRepository.getConversation(conversationId)
                ?: throw IllegalStateException("Conversation not found")

            val currentUserId = authRepository.getCurrentUserId()
                ?: throw IllegalStateException("User not logged in")

            when (conversation.type) {
                ConversationType.DIRECT -> {
                    val userId = conversation.participants
                        .first { it != currentUserId }
                    ConversationDetailData.Direct(
                        conversation = conversation,
                        user = userRepository.getUser(userId)
                            ?: throw IllegalStateException("User not found")
                    )
                }

                ConversationType.GROUP -> {
                    ConversationDetailData.Group(
                        conversation = conversation,
                        group = groupRepository.getGroup(conversationId)
                            ?: throw IllegalStateException("Group not found")
                    )
                }
            }
        } catch (_: Exception) {
            null
        }
    }
}