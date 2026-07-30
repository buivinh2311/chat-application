package com.example.core_model.result

sealed interface CreateUserResult {
    data object Success : CreateUserResult
    sealed interface Failure : CreateUserResult {
        data object Network : Failure
        data object PermissionDenied : Failure
        data object Unknown : Failure
    }
}