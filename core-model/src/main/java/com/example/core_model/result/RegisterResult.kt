package com.example.core_model.result

sealed interface RegisterResult {
    data object Success : RegisterResult

    sealed interface Failure : RegisterResult {
        data object EmailAlreadyExists : Failure
        data object InvalidEmail : Failure
        data object Network: Failure
        data object Unknown : Failure
    }
}