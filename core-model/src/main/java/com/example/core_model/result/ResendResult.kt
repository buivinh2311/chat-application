package com.example.core_model.result

sealed interface ResendResult {
    data object Success : ResendResult
    sealed interface Failure: ResendResult {
        data object Network : Failure
        data object TooManyRequests : Failure
        data object Unknown: Failure
    }
}