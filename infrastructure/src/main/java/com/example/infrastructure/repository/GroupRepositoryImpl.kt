package com.example.infrastructure.repository

import com.example.core_domain.repository.GroupRepository
import com.example.core_model.Conversation
import com.example.core_model.Group
import com.example.core_model.User
import com.example.core_network.dto.GroupDto
import com.example.infrastructure.mapper.remote.toDomain
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GroupRepository {
    companion object {
        const val GROUPS = "groups"
    }

    override suspend fun createGroup(
        name: String,
        avatarUrl: String?,
        conversationId: String,
        leaderId: String
    ): Result<Unit> {
        return try {
            val group = GroupDto(
                name = name,
                avatarUrl = avatarUrl,
                conversationId = conversationId,
                leaderId = leaderId,
                createdAt = Timestamp.now()
            )
            firestore
                .collection(GROUPS)
                .document()
                .set(group)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGroupByConversationId(
        conversationId: String
    ): Result<Group?> {
        return try {
            val group = firestore
                .collection(GROUPS)
                .whereEqualTo("conversationId", conversationId)
                .get()
                .await()
                .firstOrNull()
                ?.toObject(GroupDto::class.java)
                ?.toDomain()
            Result.success(group)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observerGroupByConversationId(
        conversationId: String
    ): Flow<Group?> = callbackFlow {
        val listener = firestore
            .collection(GROUPS)
            .whereEqualTo("conversationId", conversationId)
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val group = snapshots
                    ?.documents
                    ?.firstOrNull()
                    ?.toObject(GroupDto::class.java)
                    ?.toDomain()
                trySend(group)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun changeAvatar(
        avatarUrl: String
    ): Result<Unit> {
        return Result.success(Unit)
    }
}