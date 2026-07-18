package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.screen.ProfileScreen

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ProfileScreen(
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}