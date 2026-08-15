package com.example.core_domain.repository

import com.example.core_model.enum.Gender
import com.example.core_model.User
import com.example.core_model.enum.UserStatus
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User)

    fun observerUser(uid: String): Flow<User?>

    suspend fun getUser(uid: String): User?

    suspend fun getUsersByIds(ids: List<String>): List<User>

    suspend fun updateUser(
        uid: String,
        firstName: String,
        lastName: String,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    )

    suspend fun uploadAvatar(
        uid: String,
        imageUrl: String
    )

    suspend fun searchUser(keyword: String): List<User>

    suspend fun updateOnlineStatus(uid: String, status: UserStatus)

    suspend fun updateFcmToken(uid: String, fcmToken: String)
}