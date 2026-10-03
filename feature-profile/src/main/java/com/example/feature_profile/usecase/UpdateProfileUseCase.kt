package com.example.feature_profile.usecase

import com.example.core_domain.repository.UserRepository
import com.example.core_model.enum.Gender
import com.google.firebase.Timestamp
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        uid: String,
        fullName: String,
        bio: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ): Result<Unit> {
        return repository.updateUser(
            uid = uid,
            fullName = fullName,
            bio = bio,
            dateOfBirth = dateOfBirth,
            gender = gender
        )
    }
}