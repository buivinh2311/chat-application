package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_chat.presentation.screen.ConversationOptionScreen
import com.example.feature_chat.presentation.screen.ConversationScreen

@Composable
fun ConversationOptionRoute(
    conversationId: String,
    onBackClick: () -> Unit,
    onCreateGroup: (String) -> Unit
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
        onChangeBackgroundImage = {}
    )
}