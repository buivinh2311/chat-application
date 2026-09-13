package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserverFriendRequestUseCase @Inject constructor(
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