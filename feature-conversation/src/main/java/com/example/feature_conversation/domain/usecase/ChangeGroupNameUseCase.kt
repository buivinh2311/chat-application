package com.example.feature_conversation.domain.usecase

import com.example.core_domain.repository.GroupRepository
import javax.inject.Inject

class ChangeGroupNameUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: String, groupName: String): Result<Unit> {
        return groupRepository.changeGroupName(groupId, groupName)
    }
}