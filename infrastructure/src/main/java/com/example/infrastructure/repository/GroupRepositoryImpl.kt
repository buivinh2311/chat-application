package com.example.infrastructure.repository

import com.example.core_domain.repository.GroupRepository
import com.example.core_model.Conversation
import com.example.core_model.Group
import com.example.core_model.User
import com.example.core_network.dto.GroupDto
import com.example.infrastructure.mapper.remote.toDomain
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
        conversationId: String
    ) {
        val group = GroupDto(
            name = name,
            conversationId = conversationId
        )
        firestore
            .collection(GROUPS)
            .document()
            .set(group)
            .await()
    }

    override suspend fun getGroup(conversationId: String): Group? {
        return firestore
            .collection(GROUPS)
            .whereEqualTo("conversationId", conversationId)
            .get()
            .await()
            .firstOrNull()
            ?.toObject(GroupDto::class.java)
            ?.toDomain()
    }

    override fun observerGroup(conversationId: String): Flow<Group?> = callbackFlow {
        val listener = firestore
            .collection(GROUPS)
            .whereEqualTo("conversationId", conversationId)
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
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
}