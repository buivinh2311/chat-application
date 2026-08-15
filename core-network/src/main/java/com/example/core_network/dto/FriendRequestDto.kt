package com.example.core_network.dto

import com.example.core_model.enum.FriendRequestStatus
import com.google.firebase.Timestamp

data class FriendRequestDto(
    val senderId: String = "",
    val receiverId: String = "",
    val status: String = FriendRequestStatus.PENDING.name,
    val createdAt: Timestamp? = null
)