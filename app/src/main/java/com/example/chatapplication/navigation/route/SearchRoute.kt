package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_search.presentation.SearchScreen

@Composable
fun SearchRoute(
    selectedAction: AppBottomBarAction,
    onChatItemClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SearchScreen(
        selectedAction = selectedAction,
        onChatItemClick = onChatItemClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}