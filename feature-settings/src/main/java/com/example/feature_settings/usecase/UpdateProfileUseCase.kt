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
        avatarUrl: String?,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ): Result<Unit> {
        return repository.updateUser(
            uid = uid,
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            address = address,
            dateOfBirth = dateOfBirth,
            gender = gender
        )
    }
}