package com.example.feature_chat.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import javax.inject.Inject

class RecallFriendRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(receiverId: String): Result<Unit> {
        return try {
            val senderId = authRepository.getCurrentUserId()
                ?: throw IllegalStateException("User not logged in")
            friendRequestRepository.recallRequest(senderId, receiverId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}