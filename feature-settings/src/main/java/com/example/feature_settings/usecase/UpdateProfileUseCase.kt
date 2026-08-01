package com.example.feature_settings.usecase

import com.example.core_domain.repository.UserRepository
import com.example.core_model.Gender
import com.example.core_model.User
import com.google.firebase.Timestamp
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        uid: String,
        firstName: String,
        lastName: String,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ): Result<Unit> {
        return try {
            repository.updateUser(
                uid = uid,
                firstName = firstName,
                lastName = lastName,
                address = address,
                dateOfBirth = dateOfBirth,
                gender = gender
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}