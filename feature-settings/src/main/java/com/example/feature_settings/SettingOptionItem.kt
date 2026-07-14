package com.example.feature_settings

import androidx.compose.ui.graphics.painter.Painter

data class SettingOptionItem(
    val icon: Painter,
    val title: String,
    val action: SettingOptionAction
)