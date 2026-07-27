package com.example.core_model.result

sealed interface ResetPasswordResult {
    data object Success: ResetPasswordResult
    sealed interface Failure: ResetPasswordResult {
        data object InvalidEmail : Failure
        data object UserNotFound : Failure
        data object Network : Failure
        data object TooManyRequests : Failure
        data object Unknown : Failure
    }
}