package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.enum.ConversationType
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_chat.presentation.screen.ConversationScreen

@Composable
fun ConversationRoute(
    onSearchClick: () -> Unit,
    onNavigationToConversationDetail: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ConversationScreen(
        onSearchClick = onSearchClick,
        onNavigationToConversationDetail = onNavigationToConversationDetail,
        onBottomActionClick = onBottomActionClick
    )
}