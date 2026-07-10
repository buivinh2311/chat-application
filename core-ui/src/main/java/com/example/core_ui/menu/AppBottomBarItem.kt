package com.example.core_ui.menu

import androidx.compose.ui.graphics.painter.Painter

data class AppBottomBarItem (
    val title: String,
    val icon: Painter,
    val action: AppBottomBarAction
)