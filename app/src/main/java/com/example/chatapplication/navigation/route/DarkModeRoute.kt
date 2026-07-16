package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.screen.DarkModeScreen

@Composable
fun DarkModeRoute(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    DarkModeScreen(
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}