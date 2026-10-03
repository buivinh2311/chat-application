package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailScreen

@Composable
fun ConversationDetailRoute(
    conversationId: String,
    onOptionClick: () -> Unit,
    onOpenCamera: () -> Unit,
    onBackClick: () -> Unit
) {
    ConversationDetailScreen(
        conversationId = conversationId,
        onBackClick = onBackClick,
        onOptionClick = onOptionClick,
        onOpenCamera = onOpenCamera
    )
}