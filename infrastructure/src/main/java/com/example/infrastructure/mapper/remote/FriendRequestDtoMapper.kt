package com.example.infrastructure.mapper.remote

import com.example.core_model.FriendRequest
import com.example.core_model.enum.FriendRequestStatus
import com.example.core_network.dto.FriendRequestDto
import com.google.firebase.Timestamp

fun FriendRequestDto.toDomain(id: String): FriendRequest {
    return FriendRequest(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        status = FriendRequestStatus.valueOf(status),
        createdAt = createdAt ?: Timestamp.now()
    )
}

fun FriendRequest.toDto(): FriendRequestDto {
    return FriendRequestDto(
        senderId = senderId,
        receiverId = receiverId,
        status = status.name,
        createdAt = createdAt
    )
}