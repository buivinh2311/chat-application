package com.example.core_model.composite

import com.example.core_model.User

data class MediaPreview(
    val currentUser: User,
    val sender: User?,
    val users: List<User>,
    val messageId: String
)