package com.example.core_model

import com.google.firebase.Timestamp

data class Group(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val conversationId: String,
    val createdAt: Timestamp
)
