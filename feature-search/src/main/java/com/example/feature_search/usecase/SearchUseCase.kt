package com.example.feature_search.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(keyword: String): List<User> {
        return try {
            val currentUserId = authRepository.getCurrentUserId()
            userRepository.searchUser(keyword)
                .filter { it.id != currentUserId }
        } catch (_: Exception) {
            emptyList()
        }
    }
}