package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import javax.inject.Inject

class RecallFriendRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(receiverId: String): Result<Unit> {
        val senderId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))
        return friendRequestRepository.recallRequest(senderId, receiverId)
    }
}