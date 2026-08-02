package com.example.core_model

import com.example.core_model.enum.RequestStatus
import com.google.firebase.Timestamp

data class FriendRequest(
    val requestId: String,
    val senderId: String,
    val receiverId: String,
    val status: RequestStatus,
    val createdAt: Timestamp
)
