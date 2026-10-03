package com.example.feature_profile.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UploadCoverPhotoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(file: File): Result<Unit> {
        val currentUserId = authRepository
            .getCurrentUserId()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val uploadResult = storageRepository
            .upload(file)
            .getOrElse { return Result.failure(it) }

        return userRepository.uploadCoverPhoto(currentUserId, uploadResult.url)
    }
}