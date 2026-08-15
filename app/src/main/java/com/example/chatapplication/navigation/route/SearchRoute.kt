package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.enum.ConversationType
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_search.presentation.SearchScreen

@Composable
fun SearchRoute(
    selectedAction: AppBottomBarAction,
    onBackClick: () -> Unit,
    onNavigationToConversationDetail: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SearchScreen(
        selectedAction = selectedAction,
        onBackClick = onBackClick,
        onNavigateToConversationDetail = onNavigationToConversationDetail,
        onBottomActionClick = onBottomActionClick
    )
}