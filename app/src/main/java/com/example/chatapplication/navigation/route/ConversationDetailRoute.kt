package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.enum.ConversationType
import com.example.feature_chat.presentation.screen.ConversationDetailScreen

@Composable
fun ConversationDetailRoute(
    conversationId: String,
    onBackClick: () -> Unit,
    onOptionClick: () -> Unit
) {
    ConversationDetailScreen(
        conversationId = conversationId,
        onBackClick = onBackClick,
        onOptionClick = onOptionClick
    )
}