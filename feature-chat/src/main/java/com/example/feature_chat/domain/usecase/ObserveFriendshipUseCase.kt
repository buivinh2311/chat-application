package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendshipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveFriendshipUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendshipRepository: FriendshipRepository
) {
    operator fun invoke(uid: String): Flow<Boolean> = flow {
        val currentUserId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")
        emitAll(
            friendshipRepository.observeFriendship(uid, currentUserId)
        )
    }
}