package com.example.infrastructure.repository

import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import com.example.core_network.dto.MessageDto
import com.example.infrastructure.mapper.remote.toDomain
import com.example.infrastructure.mapper.remote.toDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.emptyList

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MessageRepository {

    private companion object {
        const val CONVERSATIONS = "conversations"
        const val MESSAGES = "messages"
    }

    private fun messagesRef(conversationId: String) = firestore
        .collection(CONVERSATIONS)
        .document(conversationId)
        .collection(MESSAGES)

    override fun observeMessagesByConversationId(
        conversationId: String,
        pageSize: Int
    ): Flow<List<Message>> = callbackFlow {
        val listener = messagesRef(conversationId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(pageSize.toLong())
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val messages = snapshots?.documents
                    ?.mapNotNull {
                        it.toObject(MessageDto::class.java)?.copy(id = it.id)?.toDomain()
                    }
                    ?: emptyList()
                trySend(messages.reversed())
            }
        awaitClose { listener.remove() }
    }

    override suspend fun loadMoreMessagesByConversationId(
        conversationId: String,
        lastVisibleTimestamp: Timestamp,
        pageSize: Int
    ): Result<List<Message>> {
        return try {
            val snapshot = messagesRef(conversationId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .startAfter(lastVisibleTimestamp)
                .limit(pageSize.toLong())
                .get()
                .await()

            Result.success(
                snapshot.documents.mapNotNull {
                    it.toObject(MessageDto::class.java)?.copy(id = it.id)?.toDomain()
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendMessage(message: Message): Result<Unit> {
        return try {
            val conversationRef = firestore
                .collection(CONVERSATIONS)
                .document(message.conversationId)

            val messageRef = conversationRef
                .collection(MESSAGES)
                .document()

            firestore.runBatch { batch ->
                batch.set(messageRef, message.toDto(messageRef.id))
                batch.update(
                    conversationRef,
                    mapOf(
                        "lastMessage" to when(message) {
                            is Message.Text -> message.text
                            is Message.Image -> "Hình ảnh"
                            is Message.Video -> "Video"
                            is Message.File -> message.fileName
                        },
                        "lastMessageTime" to message.createdAt
                    )
                )
            }.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(
        conversationId: String,
        messageId: String,
        uid: String
    ): Result<Unit> {
        return try {
            messagesRef(conversationId).document(messageId)
                .update("readBy", FieldValue.arrayUnion(uid))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}