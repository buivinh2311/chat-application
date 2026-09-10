package com.example.core_domain.repository

import com.example.core_model.Conversation
import com.example.core_model.Group
import com.example.core_model.User
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(
        name: String,
        conversationId: String
    )
    suspend fun getGroup(conversationId: String): Group?
    fun observerGroup(conversationId: String): Flow<Group?>
    suspend fun changeAvatar()
}