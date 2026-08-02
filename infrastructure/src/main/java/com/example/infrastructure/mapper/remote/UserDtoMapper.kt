package com.example.infrastructure.mapper.remote

import com.example.core_model.enum.Gender
import com.example.core_model.User
import com.example.core_model.enum.UserStatus
import com.example.core_network.dto.UserDto
import com.google.firebase.Timestamp

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        avatarUrl = avatarUrl,
        firstName = firstName,
        lastName = lastName,
        address = address,
        dateOfBirth = dateOfBirth,
        gender = gender?.let { Gender.valueOf(it) },
        status = UserStatus.valueOf(status),
        fcmTokens = fcmTokens,
        createdAt = createdAt ?: Timestamp.now()
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        address = address,
        dateOfBirth = dateOfBirth,
        gender = gender?.name,
        status = status.name,
        fcmTokens = fcmTokens,
        createdAt = createdAt
    )
}