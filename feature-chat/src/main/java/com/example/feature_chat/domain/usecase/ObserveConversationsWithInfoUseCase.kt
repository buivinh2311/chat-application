package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.composite.ConversationDisplayInfo
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_model.enum.ConversationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveConversationsWithInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(): Flow<List<ConversationWithInfo>> = flow {
        val currentUserId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")

        emitAll(
            conversationRepository
                .observeConversationsByUserId(currentUserId)
                .map {
                    it.map { conversation ->
                        if (conversation.type == ConversationType.DIRECT) {
                            val otherUserId = conversation.participants.first { it != currentUserId }
                            val otherUser = userRepository
                                .getUserById(otherUserId)
                                .getOrThrow()
                                ?: throw IllegalStateException("User not found")

                            ConversationWithInfo(
                                conversation = conversation,
                                displayInfo = ConversationDisplayInfo.Direct(otherUser)
                            )
                        } else {
                            val group = groupRepository
                                .getGroupByConversationId(conversation.id)
                                .getOrThrow()
                                ?: throw IllegalStateException("Group not found")

                            ConversationWithInfo(
                                conversation = conversation,
                                displayInfo = ConversationDisplayInfo.Group(group)
                            )
                        }
                    }
                }
        )
    }
}