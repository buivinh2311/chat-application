package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.screen.ChangePasswordScreen

@Composable
fun ChangePasswordRoute(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ChangePasswordScreen(
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}