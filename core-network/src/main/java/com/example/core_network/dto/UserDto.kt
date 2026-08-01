package com.example.core_network.dto

import com.google.firebase.Timestamp

data class UserDto(
    val id: String = "",
    val email: String = "",
    val avatarUrl: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val address: String? = null,
    val dateOfBirth: Timestamp? = null,
    val gender: String? = null,
    val status: String = "OFFLINE",
    val friends: List<String> = emptyList(),
    val fcmTokens: List<String> = emptyList(),
    val createdAt: Timestamp? = null
)