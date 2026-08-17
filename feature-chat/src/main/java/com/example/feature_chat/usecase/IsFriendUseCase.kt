package com.example.feature_chat.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class IsFriendUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRepository: FriendRepository
) {
    operator fun invoke(uid: String): Flow<Boolean> {
        val currentUserId = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")
        return friendRepository
            .isFriend(uid, currentUserId).catch { emit(false) }
    }
}