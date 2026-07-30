package com.example.core_domain.repository

import com.example.core_model.Gender
import com.example.core_model.User
import com.example.core_model.UserStatus
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User)

    fun getUser(uid: String): Flow<User?>

    suspend fun updateUser(
        uid: String,
        firstName: String,
        lastName: String,
        avatarUrl: String?,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ): Result<Unit>

    suspend fun updateOnlineStatus(uid: String, status: UserStatus)

    suspend fun updateFcmToken(uid: String, fcmToken: String)
}