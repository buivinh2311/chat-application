package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class ObserverFriendRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    operator fun invoke(receiverId: String): Flow<Boolean> {
        val senderId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("user not logged in")
        return friendRequestRepository
            .isSentFriendRequest(senderId, receiverId).catch { emit(false) }
    }
}