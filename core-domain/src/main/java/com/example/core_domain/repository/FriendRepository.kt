package com.example.core_domain.repository

import com.example.core_model.Friend
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    fun createFriend(
        transaction: Transaction,
        user1: String,
        user2: String
    ): Result<Unit>

    fun observeFriendship(user1: String, user2: String): Flow<Boolean>

    fun observeFriendsByUserId(uid: String): Flow<List<Friend>>

    suspend fun getFriendsByUserId(uid: String): Result<List<Friend>>

    suspend fun unFriend(friendId: String): Result<Unit>
}