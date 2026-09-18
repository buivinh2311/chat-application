package com.example.core_domain.repository

import com.example.core_model.Friendship
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.flow.Flow

interface FriendshipRepository {
    fun createFriendship(
        transaction: Transaction,
        user1: String,
        user2: String
    ): Result<Unit>

    fun observeFriendship(user1: String, user2: String): Flow<Boolean>

    fun observeFriendshipsByUserId(uid: String): Flow<List<Friendship>>

    suspend fun getFriendIdsByUserId(uid: String): Result<List<String>>

    suspend fun unFriend(friendId: String): Result<Unit>
}