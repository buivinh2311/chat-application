package com.example.infrastructure.repository

import com.example.core_domain.repository.AuthRepository
import com.example.core_model.result.LoginResult
import com.example.core_model.result.RegisterResult
import com.example.core_model.result.ResendResult
import com.example.core_model.result.ResetPasswordResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): LoginResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            val user = getCurrentUser() ?: return LoginResult.Failure.Unknown
            user.reload().await()
            if(!user.isEmailVerified) {
                logout()
                LoginResult.Failure.EmailNotVerified
            } else {
                LoginResult.Success
            }
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            LoginResult.Failure.InvalidCredentials
        } catch (_: FirebaseAuthInvalidUserException) {
            LoginResult.Failure.UserDisabled
        } catch (_: FirebaseNetworkException) {
            LoginResult.Failure.Network
        } catch (_: FirebaseTooManyRequestsException) {
            LoginResult.Failure.TooManyRequests
        } catch (_: Exception) {
            LoginResult.Failure.Unknown
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): RegisterResult {
        return try {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = authResult.user
                ?: return RegisterResult.Failure.Unknown

            RegisterResult.Success(firebaseUser)
        } catch (_: FirebaseAuthUserCollisionException) {
            RegisterResult.Failure.EmailAlreadyExists
        } catch (_: FirebaseAuthWeakPasswordException) {
            RegisterResult.Failure.WeakPassword
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            RegisterResult.Failure.InvalidEmail
        } catch (_: FirebaseNetworkException) {
            RegisterResult.Failure.Network
        } catch (_: FirebaseTooManyRequestsException) {
            RegisterResult.Failure.TooManyRequests
        } catch (_: Exception) {
            RegisterResult.Failure.Unknown
        }
    }

    override suspend fun sendEmailVerification(): ResendResult {
        return try {
            getCurrentUser()?.sendEmailVerification()?.await()
            ResendResult.Success
        } catch (_: FirebaseNetworkException) {
            ResendResult.Failure.Network
        } catch (_: FirebaseTooManyRequestsException) {
            ResendResult.Failure.TooManyRequests
        } catch (_: Exception) {
            ResendResult.Failure.Unknown
        }
    }

    override suspend fun sendPasswordResetEmail(
        email: String
    ): ResetPasswordResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ResetPasswordResult.Success
        } catch (_: FirebaseAuthInvalidUserException) {
            ResetPasswordResult.Failure.UserNotFound
        } catch (_: FirebaseAuthInvalidCredentialsException) {
            ResetPasswordResult.Failure.InvalidEmail
        } catch (_: FirebaseNetworkException) {
            ResetPasswordResult.Failure.Network
        } catch (_: FirebaseTooManyRequestsException) {
            ResetPasswordResult.Failure.TooManyRequests
        } catch (_: Exception) {
            ResetPasswordResult.Failure.Unknown
        }
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}