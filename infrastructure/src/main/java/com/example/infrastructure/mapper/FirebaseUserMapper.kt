package com.example.infrastructure.mapper

import com.example.core_model.User
import com.example.core_model.UserStatus
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        email = email.orEmpty(),
        avatarUrl = photoUrl?.toString(),
        firstName = "",
        lastName = "",
        address = null,
        dateOfBirth = null,
        gender = null,
        status = UserStatus.OFFLINE,
        createdAt = Timestamp.now()
    )
}