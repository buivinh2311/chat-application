package com.example.feature_auth.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.result.ResetPasswordResult
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): ResetPasswordResult {
        return repository.sendPasswordResetEmail(email)
    }
}