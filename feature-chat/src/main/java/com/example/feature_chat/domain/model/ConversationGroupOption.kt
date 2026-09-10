package com.example.feature_chat.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class ConversationGroupOption (
    val icon: Painter,
    val title: String,
    val action: ConversationGroupOptionAction
)