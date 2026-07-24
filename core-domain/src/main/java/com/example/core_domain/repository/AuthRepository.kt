package com.example.core_domain.repository

import com.example.core_model.result.RegisterResult
import com.example.core_model.User
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<User?>

    suspend fun register(
        email: String,
        password: String
    ): RegisterResult

    suspend fun sendEmailVerification(): Result<Unit>

    fun logout()

    suspend fun getCurrentUser(): FirebaseUser?
}