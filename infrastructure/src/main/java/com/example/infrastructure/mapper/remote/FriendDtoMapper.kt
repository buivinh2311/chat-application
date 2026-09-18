package com.example.infrastructure.mapper.remote

import com.example.core_model.Friendship
import com.example.core_network.dto.FriendDto
import com.google.firebase.Timestamp

fun FriendDto.toDomain(): Friendship {
    return Friendship(
        id = id,
        participants = participants,
        createdAt = createdAt ?: Timestamp.now()
    )
}

fun Friendship.toDto(): FriendDto {
    return FriendDto(
        id = id,
        participants = participants,
        createdAt = createdAt
    )
}