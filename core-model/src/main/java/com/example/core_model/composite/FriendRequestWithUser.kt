package com.example.core_model.composite

import com.example.core_model.FriendRequest
import com.example.core_model.User

data class FriendRequestWithUser(
    val request: FriendRequest,
    val user: User
)