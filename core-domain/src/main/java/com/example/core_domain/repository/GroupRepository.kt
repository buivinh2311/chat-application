package com.example.core_domain.repository

import com.example.core_model.Conversation
import com.example.core_model.Group
import com.example.core_model.User
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(
        name: String,
        avatarUrl: String? = null,
        conversationId: String,
        leaderId: String
    ): Result<Unit>

    suspend fun getGroupByConversationId(conversationId: String): Result<Group?>

    fun observerGroupByConversationId(conversationId: String): Flow<Group?>

    suspend fun changeAvatar(avatarUrl: String): Result<Unit>

}