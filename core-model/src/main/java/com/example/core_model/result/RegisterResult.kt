package com.example.core_model.result

import com.google.firebase.auth.FirebaseUser

sealed interface RegisterResult {
    data class Success(val user: FirebaseUser) : RegisterResult

    sealed interface Failure : RegisterResult {
        data object EmailAlreadyExists : Failure
        data object InvalidEmail : Failure
        data object WeakPassword : Failure
        data object Network : Failure
        data object TooManyRequests : Failure
        data object Unknown : Failure
    }
}