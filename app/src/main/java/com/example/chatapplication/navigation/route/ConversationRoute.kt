package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_conversation.presentation.conversation.ConversationScreen

@Composable
fun ConversationRoute(
    onSearchClick: () -> Unit,
    onScannerClick: () -> Unit,
    onNavigationToConversationDetail: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ConversationScreen(
        onSearchClick = onSearchClick,
        onScannerClick = onScannerClick,
        onNavigationToConversationDetail = onNavigationToConversationDetail,
        onBottomActionClick = onBottomActionClick
    )
}