package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.FriendRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import javax.inject.Inject

class GetFriendListUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val friendRepository: FriendRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return try {
            val currentUserId = authRepository.getCurrentUserId()
                ?: throw IllegalStateException("User not logged in")
        } catch (_: Exception) {
            emptyList()
        }
    }
}