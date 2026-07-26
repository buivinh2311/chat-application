package com.example.feature_auth.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.result.ResendResult
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): ResendResult {
        return repository.sendEmailVerification()
    }
}