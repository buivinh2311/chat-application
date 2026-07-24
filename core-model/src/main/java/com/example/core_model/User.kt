package com.example.core_model

data class User(
    val id: String,
    val email: String,
    val avatarUrl: String?,
    val firstName: String,
    val lastName: String,
    val address: String?,
    val dateOfBirth: Long?,
    val gender: Gender?,
    val status: UserStatus,
    val createdAt: Long
)
