package com.example.infrastructure.repository

import android.util.Log
import com.example.core_domain.repository.FriendRepository
import com.example.core_model.Friend
import com.example.core_network.dto.FriendDto
import com.example.infrastructure.mapper.remote.toDomain
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FriendRepository {
    private companion object {
        const val FRIENDS = "friends"
    }

    private fun buildFriendId(
        user1: String,
        user2: String
    ): String {
        return listOf(user1, user2)
            .sorted()
            .joinToString("_")
    }

    override fun createFriend(
        transaction: Transaction,
        user1: String,
        user2: String
    ) {
        val friendId = buildFriendId(user1, user2)
        val friend = FriendDto(
            id = friendId,
            participants = listOf(user1, user2),
            createdAt = Timestamp.now()
        )

        val friendRef = firestore
            .collection(FRIENDS)
            .document(friendId)

        transaction.set(friendRef, friend)
    }

    override fun isFriend(user1: String, user2: String): Flow<Boolean> = callbackFlow {
        val friendId = buildFriendId(user1, user2)
        val listener = firestore
            .collection(FRIENDS)
            .document(friendId)
            .addSnapshotListener { snapshot, exception ->
                if(exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                trySend(snapshot?.exists() == true)
            }
        awaitClose {
            listener.remove()
        }
    }

    override fun getFriends(uid: String): Flow<List<Friend>> = callbackFlow {
        val listener = firestore
            .collection(FRIENDS)
            .whereArrayContains("participants", uid)
            .addSnapshotListener { snapshots, exception ->
                if(exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val friends = snapshots?.documents
                    ?.mapNotNull {
                        it.toObject(FriendDto::class.java)
                            ?.toDomain()
                    }
                    ?: emptyList()
                trySend(friends)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun unFriend(friendId: String) {
        firestore
            .collection(FRIENDS)
            .document(friendId)
            .delete()
            .await()
    }
}