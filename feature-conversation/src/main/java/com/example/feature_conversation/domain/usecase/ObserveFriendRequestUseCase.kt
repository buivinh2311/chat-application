package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveFriendRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    operator fun invoke(receiverId: String): Flow<Boolean> = flow {
        val senderId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("user not logged in")
        emitAll(
            friendRequestRepository.observeSentFriendRequest(senderId, receiverId)
        )
    }
}