package com.example.feature_conversation.presentation.viewmember

import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailData

data class ViewMemberUiState (
    val conversationData: ConversationDetailData.Group,
    val currentUserId: String
)