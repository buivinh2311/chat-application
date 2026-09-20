package com.example.core_domain.repository

import com.example.core_model.enum.Gender
import com.example.core_model.User
import com.example.core_model.enum.UserStatus
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit>

    fun observerUserById(uid: String): Flow<User?>

    suspend fun getUserById(uid: String): Result<User?>

    fun observeUsersByIds(ids: List<String>): Flow<List<User>>

    suspend fun getUsersByIds(ids: List<String>): Result<List<User>>

    suspend fun updateUser(
        uid: String,
        firstName: String,
        lastName: String,
        address: String?,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ): Result<Unit>

    suspend fun uploadAvatar(
        uid: String,
        imageUrl: String
    ): Result<Unit>

    suspend fun searchUserByName(keyword: String): Result<List<User>>

    suspend fun updateOnlineStatus(uid: String, status: UserStatus): Result<Unit>

    suspend fun updateFcmToken(uid: String, fcmToken: String): Result<Unit>
}