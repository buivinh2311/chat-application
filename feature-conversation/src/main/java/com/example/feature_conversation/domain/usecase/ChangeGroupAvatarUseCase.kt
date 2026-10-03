package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.GroupRepository
import com.example.core_domain.repository.StorageRepository
import java.io.File
import javax.inject.Inject

class ChangeGroupAvatarUseCase @Inject constructor(
    private val storageRepository: StorageRepository,
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        groupId: String,
        file: File
    ): Result<Unit> {
        val avatarUrl =  storageRepository
            .upload(file)
            .getOrElse { return Result.failure(it) }
            .url

        return groupRepository.changeAvatar(groupId, avatarUrl)
    }
}