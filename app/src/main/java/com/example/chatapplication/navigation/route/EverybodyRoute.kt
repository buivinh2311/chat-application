package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_everybody.EverybodyScreen

@Composable
fun EverybodyRoute(
    onViewAllClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    EverybodyScreen(
        onViewAllClick = onViewAllClick,
        onViewMoreClick = onViewMoreClick,
        onBottomActionClick = onBottomActionClick
    )
}