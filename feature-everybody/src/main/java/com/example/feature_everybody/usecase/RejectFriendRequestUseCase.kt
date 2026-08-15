package com.example.feature_everybody.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import javax.inject.Inject

class RejectFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(requestId: String) {
        try {
            friendRequestRepository.rejectRequest(requestId)
        } catch (_: Exception) {

        }
    }
}