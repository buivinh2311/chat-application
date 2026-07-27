package com.example.core_domain.repository

import com.example.core_model.result.RegisterResult
import com.example.core_model.User
import com.example.core_model.result.LoginResult
import com.example.core_model.result.ResendResult
import com.example.core_model.result.ResetPasswordResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): LoginResult

    suspend fun register(
        email: String,
        password: String
    ): RegisterResult

    suspend fun sendEmailVerification(): ResendResult

    suspend fun sendPasswordResetEmail(email: String): ResetPasswordResult

    suspend fun logout()

    suspend fun getCurrentUser(): FirebaseUser?
}