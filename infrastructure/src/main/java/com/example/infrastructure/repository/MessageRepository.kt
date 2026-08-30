package com.example.infrastructure.repository

import android.net.Uri
import com.example.core_domain.repository.MessageRepository
import com.example.core_model.Message
import com.example.core_model.enum.MessageStatus
import com.example.core_model.enum.MessageType
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

    override fun getMessages(
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

    override suspend fun loadMoreMessages(
        conversationId: String,
        lastVisibleTimestamp: Timestamp,
        pageSize: Int
    ): List<Message> {
        val snapshot = messagesRef(conversationId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .startAfter(lastVisibleTimestamp)
            .limit(pageSize.toLong())
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(MessageDto::class.java)?.copy(id = it.id)?.toDomain()
        }
    }

    override suspend fun sendMessage(message: Message) {
        val conversationRef = firestore
            .collection(CONVERSATIONS)
            .document(message.conversationId)
        val messageRef = conversationRef
            .collection(MESSAGES)
            .document()

        firestore.runBatch { batch ->
            batch.set(messageRef, message.toDto())
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
    }

    override suspend fun markAsRead(conversationId: String, messageId: String, uid: String) {
        messagesRef(conversationId).document(messageId)
            .update("readBy", FieldValue.arrayUnion(uid))
            .await()
    }

    private fun getImageDimensions(url: String): Pair<Int, Int> {
        // Triển khai thực tế cần Context để dùng ContentResolver + BitmapFactory.Options
        // (mình để placeholder, bạn cho mình biết nếu cần code chi tiết phần này)
        return 0 to 0
    }
}