package com.example.feature_settings.usecase

import android.net.Uri
import com.example.core_domain.repository.StorageRepository
import com.example.core_domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val storageRepository: StorageRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        uid: String,
        file: File
    ): Result<Unit> {
        val uploadResult = storageRepository
            .upload(file)
            .getOrElse { return Result.failure(it) }

        return userRepository.uploadAvatar(uid, uploadResult.url)
    }
}