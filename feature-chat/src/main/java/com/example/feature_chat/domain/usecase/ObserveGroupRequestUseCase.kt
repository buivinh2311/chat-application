package com.example.feature_chat.domain.usecase

import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class ObserveGroupRequestUseCase @Inject constructor(
//    private val groupRepository: GroupRepository,
//    private val authRepository: AuthRepository
//) {
//    operator fun invoke(groupId: String): Flow<Boolean> = flow {
//        val currentUserId = authRepository
//            .getCurrentUserId()
//            ?: throw IllegalStateException("User not logged in")
//        emitAll(
//            groupRepository.
//        )
//    }
//}