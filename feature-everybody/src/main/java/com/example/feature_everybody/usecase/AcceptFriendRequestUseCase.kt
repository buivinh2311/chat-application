package com.example.feature_everybody.usecase

import com.example.core_domain.repository.FriendRepository
import com.example.core_domain.repository.FriendRequestRepository
import com.example.core_model.FriendRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AcceptFriendRequestUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val friendRepository: FriendRepository,
    private val friendRequestRepository: FriendRequestRepository
) {
    suspend operator fun invoke(request: FriendRequest) {
        firestore.runTransaction { transaction ->
            friendRepository.createFriend(
                transaction,
                request.senderId,
                request.receiverId
            )

            friendRequestRepository.deleteRequest(
                transaction,
                request.id
            )
            null
        }.await()
    }
}