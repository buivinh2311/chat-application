package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.CloudinaryUploadResult
import java.io.File
import javax.inject.Inject

class UploadGroupAvatarUseCase @Inject constructor(
    private val storageRepository: StorageRepository,
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        conversationId: String,
        file: File
    ): Result<CloudinaryUploadResult?> {
        return storageRepository.upload(file)
    }
}