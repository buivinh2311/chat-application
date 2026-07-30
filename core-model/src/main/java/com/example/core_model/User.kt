package com.example.core_model

import com.google.firebase.Timestamp


data class User(
    val id: String = "",
    val email: String = "",
    val avatarUrl: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val address: String? = null,
    val dateOfBirth: Timestamp? = null,
    val gender: Gender? = null,
    val status: UserStatus = UserStatus.ONLINE,
    val createdAt: Timestamp = Timestamp.now(),
    val fcmTokens: List<String> = emptyList()
)
