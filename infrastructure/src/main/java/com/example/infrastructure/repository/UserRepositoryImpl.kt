package com.example.infrastructure.repository

import com.example.core_domain.repository.UserRepository
import com.example.core_model.Gender
import com.example.core_model.User
import com.example.core_model.UserStatus
import com.example.core_network.dto.UserDto
import com.example.infrastructure.mapper.remote.toDomain
import com.example.infrastructure.mapper.remote.toDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): UserRepository {
    private companion object{
        const val USERS = "users"
    }

    override suspend fun createUser(user: User) {
        firestore
            .collection(USERS)
            .document(user.id)
            .set(user.toDto())
            .await()
    }

    override fun getUser(uid: String): Flow<User?> = callbackFlow {
        val listener = firestore
            .collection(USERS)
            .document(uid)
            .addSnapshotListener { snapshot, exception ->
                if(exception != null) {
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