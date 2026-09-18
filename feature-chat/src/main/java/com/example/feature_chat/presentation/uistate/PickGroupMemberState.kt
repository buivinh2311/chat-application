package com.example.feature_chat.presentation.uistate

import com.example.core_model.User

data class PickGroupMemberState(
    val friends: List<User>,
    val groupMember: List<User>,
    val isGroupNameBlank: Boolean
)