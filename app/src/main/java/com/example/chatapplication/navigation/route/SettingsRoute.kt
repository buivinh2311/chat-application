package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.menu.SettingOptionAction
import com.example.feature_settings.presentation.screen.SettingsScreen

@Composable
fun SettingsRoute(
    onSettingOptionClick: (SettingOptionAction) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SettingsScreen(
        onSettingOptionClick = onSettingOptionClick,
        onBottomActionClick = onBottomActionClick
    )
}