package com.example.core_model.result

sealed interface SendFriendRequestResult {
    data object Success: SendFriendRequestResult
    data object Failure: SendFriendRequestResult
}