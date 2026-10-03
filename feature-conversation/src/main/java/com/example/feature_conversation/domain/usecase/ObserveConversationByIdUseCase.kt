package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.enum.ConversationType
import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveConversationByIdUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val conversationRepository: ConversationRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        conversationId: String
    ): Flow<ConversationDetailData> = flow {

        val currentUserId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")

        emitAll(
            conversationRepository
                .observerConversationById(conversationId)
                .flatMapLatest { conversation ->
                    conversation
                        ?: throw IllegalStateException("Conversation not found: $conversationId")

                    when (conversation.type) {
                        ConversationType.DIRECT -> {
                            val userId = conversation.participants
                                .firstOrNull { it != currentUserId }
                                ?: throw IllegalStateException("Other user not found")

                            userRepository
                                .observerUserById(userId)
                                .map { user ->
                                    user ?: throw IllegalStateException("User not found: $userId")
                                    ConversationDetailData.Direct(
                                        conversation = conversation,
                                        user = user
                                    )
                                }
                        }

                        ConversationType.GROUP -> {
                            userRepository
                                .observeUsersByIds(conversation.participants)
                                .combine(
                                    groupRepository
                                        .observerGroupByConversationId(conversationId)
                                ) { users, group ->
                                    val nonNullGroup = group
                                        ?: throw IllegalStateException(
                                            "Group not found: $conversationId"
                                        )

                                    ConversationDetailData.Group(
                                        conversation = conversation,
                                        group = nonNullGroup,
                                        users = users.associateBy { it.id }
                                    )
                                }
                        }
                    }
                }
        )
    }
}