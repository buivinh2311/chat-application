package com.example.feature_auth.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.User
import com.example.core_model.result.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}