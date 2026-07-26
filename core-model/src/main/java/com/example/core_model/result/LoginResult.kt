package com.example.core_model.result

sealed interface LoginResult {
    data object Success : LoginResult
    sealed interface Failure: LoginResult {
        data object InvalidCredentials : Failure
        data object UserDisabled : Failure
        data object Network : Failure
        data object TooManyRequests : Failure
        data object EmailNotVerified : Failure
        data object Unknown : Failure
    }
}