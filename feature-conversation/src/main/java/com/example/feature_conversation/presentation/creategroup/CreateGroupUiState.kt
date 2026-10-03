package com.example.feature_conversation.presentation.creategroup

import com.example.core_model.User

data class CreateGroupUiState(
    val friends: List<User>,
    val groupMember: List<User>,
    val isGroupNameBlank: Boolean
)