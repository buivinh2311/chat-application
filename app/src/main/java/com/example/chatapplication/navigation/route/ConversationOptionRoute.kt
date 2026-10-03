package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.conversationoption.ConversationOptionScreen

@Composable
fun ConversationOptionRoute(
    conversationId: String,
    onBackClick: () -> Unit,
    onCreateGroup: (String) -> Unit,
    onViewGroupMember: (String) -> Unit
) {
    ConversationOptionScreen(
        conversationId = conversationId,
        onBackClick = onBackClick,
        onBlock = {},
        onUnFriend = {},
        onViewMedia = {},
        onCreateGroup = onCreateGroup,
        onDeleteChatHistory = {},
        onTurnOnNotification = {},
        onTurnOffNotification = {},
        onChangeBackgroundImage = {},
        onViewGroupMember = onViewGroupMember
    )
}