package com.example.core_network.dto

import com.google.firebase.Timestamp

data class FriendDto (
    val id: String = "",
    val participants: List<String> = emptyList(),
    val createdAt: Timestamp? = null
)