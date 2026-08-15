package com.example.infrastructure.mapper.remote

import com.example.core_model.Friend
import com.example.core_network.dto.FriendDto
import com.google.firebase.Timestamp

fun FriendDto.toDomain(): Friend {
    return Friend(
        id = id,
        participants = participants,
        createdAt = createdAt ?: Timestamp.now()
    )
}

fun Friend.toDto(): FriendDto {
    return FriendDto(
        id = id,
        participants = participants,
        createdAt = createdAt
    )
}