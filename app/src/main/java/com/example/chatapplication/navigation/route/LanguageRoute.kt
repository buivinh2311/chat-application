package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.screen.LanguageScreen

@Composable
fun LanguageRoute(
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    LanguageScreen(
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}