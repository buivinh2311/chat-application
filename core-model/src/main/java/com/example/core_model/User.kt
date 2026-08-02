package com.example.core_model

import com.example.core_model.enum.Gender
import com.example.core_model.enum.UserStatus
import com.google.firebase.Timestamp


data class User(
    val id: String,
    val email: String,
    val avatarUrl: String? = null,
    val firstName: String,
    val lastName: String,
    val address: String? = null,
    val dateOfBirth: Timestamp? = null,
    val gender: Gender? = null,
    val status: UserStatus = UserStatus.OFFLINE,
    val fcmTokens: List<String> = emptyList(),
    val createdAt: Timestamp
)
