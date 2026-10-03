package com.example.core_domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserverUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> = flow {
        val uid = authRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")

        emitAll(
            userRepository.observerUserById(uid)
        )
    }
}