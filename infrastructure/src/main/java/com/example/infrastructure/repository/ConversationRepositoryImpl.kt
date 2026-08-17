package com.example.infrastructure.repository

import android.util.Log
import com.example.core_domain.repository.ConversationRepository
import com.example.core_model.Conversation
import com.example.core_model.User
import com.example.core_model.enum.ConversationType
import com.example.core_network.dto.ConversationDto
import com.example.infrastructure.mapper.remote.toDomain
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ConversationRepository {
    companion object {
        const val CONVERSATIONS = "conversations"
    }

    fun buildConversationId(
        user1: String,
        user2: String
    ): String {
        return listOf(user1, user2)
            .sorted()
            .joinToString("_")
    }

    override suspend fun getConversation(conversationId: String): Conversation? {
        return firestore
            .collection(CONVERSATIONS)
            .document(conversationId)
            .get()
            .await()
            ?.toObject(ConversationDto::class.java)
            ?.toDomain()
    }

    override fun observerConversation(conversationId: String): Flow<Conversation?> = callbackFlow {
        val listener = firestore
            .collection(CONVERSATIONS)
            .document(conversationId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val conversation = snapshot
                    ?.toObject(ConversationDto::class.java)
                    ?.toDomain()
                trySend(conversation)
            }
        awaitClose {
            listener.remove()
        }
    }

    override fun getAllConversation(userId: String): Flow<List<Conversation>> = callbackFlow{
        val listener = firestore
            .collection(CONVERSATIONS)
            .whereArrayContains("participants", userId)
            .addSnapshotListener { snapshots, exception ->
                if(exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val conversations = snapshots?.documents
                    ?.mapNotNull {
                        it.toObject(ConversationDto::class.java)
                            ?.toDomain()
                    }
                    ?: emptyList()
                trySend(conversations)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun ensureConversation(
        user1: FirebaseUser,
        user2: User
    ): String {
        val conversationId = buildConversationId(user1.uid, user2.id)
        val ref = firestore
            .collection(CONVERSATIONS)
            .document(conversationId)
        if (!ref.get().await().exists()) {
            ref.set(
                ConversationDto(
                    id = conversationId,
                    type = ConversationType.DIRECT.name,
                    memberAvatars = listOf(user1.photoUrl.toString(), user2.id),
                    participants = listOf(user1.uid, user2.id),
                    createdAt = Timestamp.now()
                )
            ).await()
        }
        return conversationId
    }
}