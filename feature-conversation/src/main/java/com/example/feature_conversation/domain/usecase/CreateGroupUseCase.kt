package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.ConversationRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.User
import java.io.File
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val groupRepository: GroupRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        avatar: File? = null,
        otherUser: List<User>
    ): Result<String> {
        val currentUser = authRepository.getCurrentUser()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val conversationId = conversationRepository
            .createConversation(currentUser, otherUser)
            .getOrElse { return Result.failure(it) }

        val avatarUrl = if(avatar != null) {
            storageRepository
                .upload(avatar)
                .getOrElse { return Result.failure(it) }
                .url
        } else {
            null
        }

        groupRepository
            .createGroup(
                name = name,
                avatarUrl = avatarUrl,
                conversationId = conversationId,
                leaderId = currentUser.uid
            )
            .getOrElse { return Result.failure(it) }

        return Result.success(conversationId)
    }
}