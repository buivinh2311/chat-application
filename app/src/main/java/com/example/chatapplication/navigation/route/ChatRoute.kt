package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_chat.screen.ChatScreen

@Composable
fun ChatRoute(
    onChatItemClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ChatScreen(
        onChatItemClick = onChatItemClick,
        onBottomActionClick = onBottomActionClick
    )
}