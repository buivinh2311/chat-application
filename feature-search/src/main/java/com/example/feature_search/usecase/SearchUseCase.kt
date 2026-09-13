package com.example.feature_search.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(keyword: String): Result<List<User>> {
        val currentUserId = authRepository.getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val users = userRepository
            .searchUserByName(keyword)
            .getOrElse { return Result.failure(it) }

        return Result.success(
            users.filter {
                it.id != currentUserId
            }
        )
    }
}