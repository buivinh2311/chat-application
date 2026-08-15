package com.example.core_model

import com.example.core_model.enum.FriendRequestStatus
import com.google.firebase.Timestamp

data class FriendRequest(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val status: FriendRequestStatus,
    val createdAt: Timestamp
)
