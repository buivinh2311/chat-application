package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.enum.ConversationType
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import javax.inject.Inject

class GetConversationByIdUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String): Result<ConversationDetailData> {
        val conversation = conversationRepository
            .getConversationById(conversationId)
            .getOrElse { return Result.failure(it) }
            ?: return Result.failure(IllegalStateException("conversation not found"))

        val currentUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        return when (conversation.type) {
            ConversationType.DIRECT -> {
                val userId = conversation.participants
                    .first { it != currentUserId }

                val user = userRepository
                    .getUserById(userId)
                    .getOrElse { return Result.failure(it) }
                    ?: return Result.failure(IllegalStateException("User not found"))

                Result.success(
                    ConversationDetailData.Direct(
                        conversation = conversation,
                        user = user
                    )
                )
            }

            ConversationType.GROUP -> {
                val users = userRepository
                    .getUsersByIds(conversation.participants)
                    .getOrElse { return Result.failure(it) }

                val group = groupRepository
                    .getGroupByConversationId(conversationId)
                    .getOrElse { return Result.failure(it) }
                    ?: return Result.failure(IllegalStateException("Group not found"))

                Result.success(
                    ConversationDetailData.Group(
                        conversation = conversation,
                        group = group,
                        users = users.associateBy { it.id }
                    )
                )
            }
        }
    }
}