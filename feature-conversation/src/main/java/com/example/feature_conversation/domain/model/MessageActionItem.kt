package com.example.feature_conversation.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class MessageActionItem (
    val icon: Painter,
    val tint: Color,
    val title: String,
    val action: MessageAction
)