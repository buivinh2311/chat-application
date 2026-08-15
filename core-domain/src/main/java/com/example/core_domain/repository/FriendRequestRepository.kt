package com.example.core_domain.repository

import com.example.core_model.FriendRequest
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.flow.Flow

interface FriendRequestRepository {
    suspend fun sendFriendRequest(senderId: String, receiverId: String)
    fun isSentFriendRequest(senderId: String, receiverId: String): Flow<Boolean>
    fun getReceivedRequests(uid: String): Flow<List<FriendRequest>>
    suspend fun rejectRequest(
        requestId: String
    )
    fun deleteRequest(
        transaction: Transaction,
        requestId: String
    )
    suspend fun recallRequest(senderId: String, receiverId: String)
}