package com.example.chatapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailScreen

@Composable
fun ConversationDetailRoute(
    conversationId: String,
    onOption: () -> Unit,
    onOpenCamera: () -> Unit,
    onBack: () -> Unit
) {
    ConversationDetailScreen(
        conversationId = conversationId,
        onBack = onBack,
        onOption = onOption,
        onOpenCamera = onOpenCamera
    )
}