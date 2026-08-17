package com.example.feature_chat.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.Conversation
import com.example.core_model.composite.ConversationDisplayInfo
import com.example.core_model.composite.ConversationWithInfo
import com.example.core_model.enum.ConversationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetAllConversationWithInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(): Flow<List<ConversationWithInfo>> {
        val currentUserId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")
        val conversations = conversationRepository
            .getAllConversation(currentUserId).catch { emit(emptyList()) }
        return conversations.mapNotNull {
            it.map { conversation ->
                if(conversation.type == ConversationType.DIRECT) {
                    val otherUserId = conversation.participants.first { it != currentUserId }
                    val otherUser = userRepository.getUser(otherUserId)
                        ?: throw IllegalStateException("User not found")
                    ConversationWithInfo(
                        conversation = conversation,
                        displayInfo = ConversationDisplayInfo.Direct(otherUser)
                    )
                } else {
                    val group = groupRepository.getGroup(conversation.id)
                        ?: throw IllegalStateException("Group not found")
                    ConversationWithInfo(
                        conversation = conversation,
                        displayInfo = ConversationDisplayInfo.Group(group)
                    )
                }
            }
        }
    }
}