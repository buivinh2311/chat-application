package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_everybody.presentation.EverybodyScreen

@Composable
fun EverybodyRoute(
    onSearchClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onViewMoreClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    EverybodyScreen(
        onSearchClick = onSearchClick,
        onViewAllClick = onViewAllClick,
        onViewMoreClick = onViewMoreClick,
        onBottomActionClick = onBottomActionClick
    )
}