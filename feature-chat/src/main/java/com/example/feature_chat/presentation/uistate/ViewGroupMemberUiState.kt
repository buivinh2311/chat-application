package com.example.feature_chat.presentation.uistate

import com.example.core_model.User

data class ViewGroupMemberUiState (
    val conversationData: ConversationDetailData.Group,
    val currentUserId: String
)