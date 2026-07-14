package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.SettingsScreen

@Composable
fun SettingsRoute(
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SettingsScreen(
        onBottomActionClick = onBottomActionClick
    )
}