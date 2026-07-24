package com.example.feature_auth.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.result.RegisterResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResult {
        return repository.register(email, password)
    }
}