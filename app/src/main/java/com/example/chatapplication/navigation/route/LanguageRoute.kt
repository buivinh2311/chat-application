package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.language.LanguageScreen

@Composable
fun LanguageRoute(
    onBack: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    LanguageScreen(
        onBack = onBack,
        onBottomActionClick = onBottomActionClick
    )
}