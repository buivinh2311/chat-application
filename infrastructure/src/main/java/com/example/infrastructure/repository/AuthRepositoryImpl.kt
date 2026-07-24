package com.example.infrastructure.repository

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.User
import com.example.core_model.result.RegisterResult
import com.example.infrastructure.mapper.toUser
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<User?> {
        return try {
            val firebaseUser = firebaseAuth
                .signInWithEmailAndPassword(
                    email,
                    password
                )
                .await()
                .user
            Result.success(firebaseUser?.toUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
            RegisterResult.Success
        } catch (_: FirebaseAuthUserCollisionException) {
            RegisterResult.Failure.EmailAlreadyExists
        } catch (_: FirebaseNetworkException) {
            RegisterResult.Failure.Network
        } catch (_: Exception) {
            RegisterResult.Failure.Unknown
        }
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            getCurrentUser()?.sendEmailVerification()?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}