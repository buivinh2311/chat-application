package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): User? {
        return try {
            userRepository.getUser(uid)
        } catch (_: Exception) {
            null
        }
    }
}