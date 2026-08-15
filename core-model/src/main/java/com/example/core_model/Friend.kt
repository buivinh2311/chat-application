package com.example.core_model

import com.google.firebase.Timestamp

data class Friend (
    val id: String,
    val participants: List<String>,
    val createdAt: Timestamp
)