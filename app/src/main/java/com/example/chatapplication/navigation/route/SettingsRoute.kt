package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_model.enum.SettingOptionAction
import com.example.feature_settings.presentation.settings.SettingsScreen

@Composable
fun SettingsRoute(
    onSearchClick: () -> Unit,
    onSettingOptionClick: (SettingOptionAction) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SettingsScreen(
        onSearchClick = onSearchClick,
        onSettingOptionClick = onSettingOptionClick,
        onBottomActionClick = onBottomActionClick
    )
}