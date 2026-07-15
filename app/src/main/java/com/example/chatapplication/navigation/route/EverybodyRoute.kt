package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_everybody.EverybodyScreen

@Composable
fun EverybodyRoute(
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    EverybodyScreen(
        onBottomActionClick = onBottomActionClick
    )
}