package com.example.feature_conversation.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class ConversationDirectOption (
    val icon: Painter,
    val title: String,
    val action: ConversationDirectOptionAction
)