package com.example.infrastructure.repository

import android.util.Log
import com.example.core_domain.repository.UserRepository
import com.example.core_model.enum.Gender
import com.example.core_model.User
import com.example.core_model.enum.UserStatus
import com.example.core_network.dto.UserDto
import com.example.infrastructure.mapper.remote.toDomain
import com.example.infrastructure.mapper.remote.toDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {
    private companion object {
        const val USERS = "users"
    }

    override suspend fun createUser(user: User) {
        firestore
            .collection(USERS)
            .document(user.id)
            .set(user.toDto())
            .await()
    }

    override fun observerUser(uid: String): Flow<User?> = callbackFlow {
        val listener = firestore
            .collection(USERS)
            .document(uid)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                trySend(
                    snapshot
                        ?.toObject(UserDto::class.java)
                        ?.toDomain()
                )
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getUser(uid: String): User? {
        return firestore
            .collection(USERS)
            .document(uid)
            .get()
            .await()
            ?.toObject(UserDto::class.java)
            ?.toDomain()
    }

    override suspend fun getUsersByIds(ids: List<String>): List<User> {
        if (ids.isEmpty()) {
            return emptyList()
        }
        return firestore
            .collection(USERS)
            .whereIn(FieldPath.documentId(), ids)
            .get()
            .await()
            .documents
            .mapNotNull {
                it.toObject(UserDto::class.java)?.toDomain()
            }
    }

    override suspend fun updateUser(
        uid: String,
        firstName: String,
        lastName: String,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ) {
        firestore
            .collection(USERS)
            .document(uid)
            .update(
                mapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "address" to address,
                    "dateOfBirth" to dateOfBirth,
                    "gender" to gender.toString()
                )
            )
            .await()
    }

    override suspend fun uploadAvatar(
        uid: String,
        imageUrl: String
    ) {
        firestore
            .collection(USERS)
            .document(uid)
            .update("avatarUrl", imageUrl)
            .await()
        Result.success(Unit)
    }

    override suspend fun searchUser(keyword: String): List<User> = coroutineScope {
        val key = keyword.trim()
        if (key.isBlank()) {
            return@coroutineScope emptyList()
        }

        val firstNameDeferred = async {
            firestore
                .collection(USERS)
                .whereGreaterThanOrEqualTo("firstName", key)
                .whereLessThanOrEqualTo("firstName", key + "\uf8ff")
                .get()
                .await()
        }

        val lastNameDeferred = async {
            firestore
                .collection(USERS)
                .whereGreaterThanOrEqualTo("lastName", key)
                .whereLessThanOrEqualTo("lastName", key + "\uf8ff")
                .get()
                .await()
        }

        val firstNameSnapshot = firstNameDeferred.await()
        val lastNameSnapshot = lastNameDeferred.await()

        return@coroutineScope (firstNameSnapshot.documents + lastNameSnapshot.documents)
            .mapNotNull {
                it.toObject(UserDto::class.java)?.toDomain()
            }
            .distinctBy { it.id }
    }

    override suspend fun updateOnlineStatus(
        uid: String,
        status: UserStatus
    ) {
        firestore
            .collection(USERS)
            .document(uid)
            .update(
                mapOf(

                )
            )
            .await()
    }

    override suspend fun updateFcmToken(uid: String, fcmToken: String) {
        firestore
            .collection(USERS)
            .document(uid)
            .update(
                "fcmTokens",
                FieldValue.arrayUnion(fcmToken)
            )
            .await()
    }
}